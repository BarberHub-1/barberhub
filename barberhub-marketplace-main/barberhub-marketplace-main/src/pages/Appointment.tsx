import React, { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { useAuth } from '../contexts/AuthContext';
import { estabelecimentoService } from '../services/estabelecimento.service';
import Navigation from '../components/Navigation';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Spinner } from '../components/Spinner';
import { toast } from 'react-toastify';
import { FaCalendarAlt, FaClock, FaUser, FaMapMarkerAlt } from 'react-icons/fa';
import { Calendar } from '@/components/ui/calendar';
import { Popover, PopoverContent, PopoverTrigger } from '@/components/ui/popover';
import { format } from "date-fns";
import { ptBR } from 'date-fns/locale';
import { cn } from '@/lib/utils';
import { agendamentoService } from '../services/agendamento.service';

interface Servico {
  id: number;
  tipo: string;
  descricao: string;
  preco: number;
  duracaoMinutos: number;
}

interface BarberShop {
  id: number;
  nomeEstabelecimento: string;
  endereco: string;
  cidade: string;
  servicos: Servico[];
}

const Appointment = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const { user } = useAuth();
  const [selectedService, setSelectedService] = useState<Servico | null>(null);
  const [selectedDate, setSelectedDate] = useState<Date | undefined>(undefined);
  const [selectedTime, setSelectedTime] = useState<string>('');

  // Verificar se o usuário está logado
  React.useEffect(() => {
    if (!user) {
      toast.error('Você precisa estar logado para fazer um agendamento');
      navigate('/login');
    }
  }, [user, navigate]);

  const { data: barbershop, isLoading, error } = useQuery<BarberShop>({
    queryKey: ['barbershop', id],
    queryFn: async () => {
      try {
        const response = await estabelecimentoService.getById(Number(id));
        console.log('Resposta da API:', response);
        
        // Processar os serviços para garantir que todos os campos necessários existam
        const processedResponse = {
          ...response,
          servicos: response.servicos.map((servico, index) => {
            // Se o serviço for uma string, tentar converter para objeto
            if (typeof servico === 'string') {
              const servicoStr = servico as string;
              try {
                return {
                  id: index + 1,
                  tipo: servicoStr.match(/tipo=([^,]+)/)?.[1] || '',
                  descricao: servicoStr.match(/descricao=([^,]+)/)?.[1] || '',
                  preco: parseFloat(servicoStr.match(/preco=([^,]+)/)?.[1] || '0'),
                  duracaoMinutos: parseInt(servicoStr.match(/duracaoMinutos=(\d+)/)?.[1] || '0')
                };
              } catch (error) {
                console.error('Erro ao converter serviço:', error);
                return {
                  id: index + 1,
                  tipo: '',
                  descricao: servicoStr,
                  preco: 0,
                  duracaoMinutos: 0
                };
              }
            }
            
            // Se já for um objeto, garantir que todos os campos existam
            const servicoObj = servico as Servico;
            return {
              id: servicoObj.id || index + 1,
              tipo: servicoObj.tipo || '',
              descricao: servicoObj.descricao || '',
              preco: typeof servicoObj.preco === 'number' ? servicoObj.preco : 0,
              duracaoMinutos: servicoObj.duracaoMinutos || 0
            };
          })
        };
        
        console.log('Resposta processada:', processedResponse);
        return processedResponse;
      } catch (error) {
        console.error('Erro ao buscar detalhes da barbearia:', error);
        throw error;
      }
    },
    enabled: !!id,
  });

  // Gerar horários disponíveis (exemplo: das 9h às 18h)
  const availableTimes = React.useMemo(() => {
    const times = [];
    for (let hour = 9; hour <= 18; hour++) {
      times.push(`${hour.toString().padStart(2, '0')}:00`);
      times.push(`${hour.toString().padStart(2, '0')}:30`);
    }
    return times;
  }, []);

  // Gerar datas disponíveis (próximos 7 dias)
  const availableDates = React.useMemo(() => {
    const dates = [];
    const today = new Date();
    for (let i = 0; i < 7; i++) {
      const date = new Date(today);
      date.setDate(today.getDate() + i);
      dates.push(date);
    }
    return dates;
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!user?.id) {
      toast.error('Usuário não autenticado');
      return;
    }

    if (!selectedService || !selectedService.id) {
      toast.error('Por favor, selecione um serviço');
      return;
    }

    if (!selectedDate) {
      toast.error('Por favor, selecione uma data');
      return;
    }

    if (!selectedTime) {
      toast.error('Por favor, selecione um horário');
      return;
    }

    try {
      // Criar uma nova data com o horário selecionado
      const dataHora = new Date(selectedDate);
      const [hours, minutes] = selectedTime.split(':');
      dataHora.setHours(parseInt(hours), parseInt(minutes), 0, 0);

      // Ajustar para o fuso horário local
      const dataHoraLocal = new Date(dataHora.getTime() - dataHora.getTimezoneOffset() * 60000);

      // Verificar se a data é futura
      if (dataHoraLocal <= new Date()) {
        toast.error('A data e hora devem ser futuras');
        return;
      }

      console.log('Data e hora selecionadas:', {
        dataHoraOriginal: dataHora,
        dataHoraLocal: dataHoraLocal,
        timezoneOffset: dataHora.getTimezoneOffset(),
        isoString: dataHoraLocal.toISOString()
      });

      const agendamentoData = {
        clienteId: user.id,
        estabelecimentoId: Number(id),
        servicos: [selectedService.id],
        dataHora: dataHoraLocal.toISOString()
      };
      
      console.log('Dados do agendamento a serem enviados:', agendamentoData);
      console.log('Serviço selecionado:', selectedService);

      const response = await agendamentoService.criarAgendamento(agendamentoData);
      console.log('Resposta do servidor:', response);

      toast.success('Agendamento realizado com sucesso!');
      navigate('/client/appointments');
    } catch (error: any) {
      console.error('Erro ao realizar agendamento:', error);
      console.error('Detalhes do erro:', error.response?.data);
      toast.error(error.response?.data?.message || 'Erro ao realizar agendamento');
    }
  };

  if (isLoading) {
    return (
      <div className="min-h-screen bg-gray-50">
        <Navigation />
        <div className="container mx-auto px-4 py-8 mt-20">
          <div className="flex justify-center items-center min-h-[60vh]">
            <Spinner />
          </div>
        </div>
      </div>
    );
  }

  if (error || !barbershop) {
    return (
      <div className="min-h-screen bg-gray-50">
        <Navigation />
        <div className="container mx-auto px-4 py-8 mt-20">
          <div className="text-center">
            <h1 className="text-2xl font-bold text-red-600">Erro ao carregar detalhes da barbearia</h1>
            <p className="mt-2 text-gray-600">Por favor, tente novamente mais tarde.</p>
            <Button 
              onClick={() => navigate('/barbershops')}
              className="mt-4 bg-gray-600 hover:bg-gray-700"
            >
              Voltar para Barbearias
            </Button>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <Navigation />
      <div className="container mx-auto px-4 py-8 mt-20">
        <Button 
          onClick={() => navigate(`/barbershops/${id}`)}
          variant="outline"
          className="mb-6"
        >
          ← Voltar
        </Button>

        <div className="max-w-2xl mx-auto">
          <Card>
            <CardHeader>
              <CardTitle className="flex items-center gap-2">
                <FaCalendarAlt className="text-blue-600" />
                Agendar Horário
              </CardTitle>
            </CardHeader>
            <CardContent>
              <div className="mb-6">
                <h2 className="text-xl font-semibold text-gray-800 mb-2">{barbershop.nomeEstabelecimento}</h2>
                <div className="flex items-center text-gray-600">
                  <FaMapMarkerAlt className="mr-2" />
                  <span>{barbershop.endereco}, {barbershop.cidade}</span>
                </div>
              </div>

              <form onSubmit={handleSubmit} className="space-y-6">
                {/* Seleção de Serviço */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Serviço
                  </label>
                  <select
                    className="w-full p-2 border rounded-lg"
                    value={selectedService?.id || ''}
                    onChange={(e) => {
                      const service = barbershop.servicos.find(s => s.id === Number(e.target.value));
                      setSelectedService(service || null);
                    }}
                    required
                  >
                    <option value="">Selecione um serviço</option>
                    {barbershop.servicos.map((servico) => (
                      <option key={servico.id} value={servico.id}>
                        {servico.descricao} - R$ {typeof servico.preco === 'number' ? servico.preco.toFixed(2) : '0.00'} ({servico.duracaoMinutos || 0} min)
                      </option>
                    ))}
                  </select>
                </div>

                {/* Seleção de Data */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Data
                  </label>
                  <Popover>
                    <PopoverTrigger asChild>
                      <Button
                        variant={"outline"}
                        className={cn(
                          "w-full justify-start text-left font-normal",
                          !selectedDate && "text-muted-foreground"
                        )}
                      >
                        <FaCalendarAlt className="mr-2 h-4 w-4" />
                        {selectedDate ? format(selectedDate, "PPP", { locale: ptBR }) : <span className="text-gray-500">Selecione uma data</span>}
                      </Button>
                    </PopoverTrigger>
                    <PopoverContent className="w-auto p-0">
                      <Calendar
                        mode="single"
                        selected={selectedDate}
                        onSelect={setSelectedDate}
                        initialFocus
                        disabled={(date) => {
                          return !availableDates.some(availableDate => 
                            availableDate.getFullYear() === date.getFullYear() &&
                            availableDate.getMonth() === date.getMonth() &&
                            availableDate.getDate() === date.getDate()
                          );
                        }}
                        locale={ptBR}
                        formatters={{
                          formatWeekdayName: (date, options) => format(date, 'EEEEEE', options),
                        }}
                      />
                    </PopoverContent>
                  </Popover>
                </div>

                {/* Seleção de Horário */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Horário
                  </label>
                  <div className="grid grid-cols-4 gap-2">
                    {availableTimes.map((time) => (
                      <button
                        key={time}
                        type="button"
                        onClick={() => setSelectedTime(time)}
                        className={`px-3 py-2 border rounded-lg text-sm ${selectedTime === time ? 'bg-gray-600 text-white' : 'bg-white text-gray-700 hover:bg-gray-100'}`}
                      >
                        {time}
                      </button>
                    ))}
                  </div>
                </div>

                {/* Resumo do Agendamento */}
                {selectedService && selectedDate && selectedTime && (
                  <div className="bg-gray-50 p-4 rounded-lg">
                    <h3 className="font-medium text-gray-800 mb-2">Resumo do Agendamento</h3>
                    <div className="space-y-2">
                      <p className="text-gray-600">
                        <span className="font-medium">Serviço:</span> {selectedService.descricao}
                      </p>
                      <p className="text-gray-600">
                        <span className="font-medium">Data:</span>{' '}
                        {format(selectedDate, "PPP", { locale: ptBR })}
                      </p>
                      <p className="text-gray-600">
                        <span className="font-medium">Horário:</span> {selectedTime}
                      </p>
                      <p className="text-gray-600">
                        <span className="font-medium">Duração:</span> {selectedService.duracaoMinutos} minutos
                      </p>
                      <p className="text-gray-600">
                        <span className="font-medium">Valor:</span> R$ {selectedService.preco.toFixed(2)}
                      </p>
                    </div>
                  </div>
                )}

                <Button
                  type="submit"
<<<<<<< HEAD
                  className="w-full bg-gray-600 hover:bg-gray-700 text-white py-6 text-lg"
=======
                  className="w-full bg-gray-900 hover:bg-gray-800 text-white py-6 text-lg"
>>>>>>> 657d6f7 (add avaliações cards)
                >
                  Confirmar Agendamento
                </Button>
              </form>
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  );
};

export default Appointment; 