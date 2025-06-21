import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { useAuth } from '../contexts/AuthContext';
import { estabelecimentoService } from '../services/estabelecimento.service';
import Navigation from '../components/Navigation';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Spinner } from '../components/Spinner';
import { useToast } from '@/hooks/use-toast';
import { FaCalendarAlt, FaClock, FaUser, FaMapMarkerAlt } from 'react-icons/fa';
import { Calendar } from '@/components/ui/calendar';
import { Popover, PopoverContent, PopoverTrigger } from '@/components/ui/popover';
import { format } from "date-fns";
import { ptBR } from 'date-fns/locale';
import { cn } from '@/lib/utils';
import { agendamentoService } from '../services/agendamento.service';
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog"

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
  rua: string;
  numero: number;
  bairro: string;
  cidade: string;
  servicos: Servico[];
}

const AppointmentContent = ({ barbershop, availableTimes, handleSubmit, selectedService, setSelectedService, selectedDate, setSelectedDate, selectedTime, setSelectedTime, disabledDays }: { barbershop: BarberShop, availableTimes: string[], handleSubmit: (e: React.FormEvent) => Promise<void>, selectedService: Servico | null, setSelectedService: React.Dispatch<React.SetStateAction<Servico | null>>, selectedDate: Date | undefined, setSelectedDate: React.Dispatch<React.SetStateAction<any>>, selectedTime: string, setSelectedTime: React.Dispatch<React.SetStateAction<string>>, disabledDays: (({ date }: { date: Date; }) => boolean )}) => (
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
            <span>
              {barbershop.rua}, {barbershop.numero} - {barbershop.bairro}, {barbershop.cidade}
            </span>
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
                        {servico.descricao} - R$ {typeof servico.preco === 'number' ? servico.preco.toFixed(2) : 'N/A'} ({servico.duracaoMinutos || 0} min)
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
                        value={selectedDate}
                        onChange={(value: any) => {
                          // react-calendar pode retornar um array para range, então garantimos que é uma data
                          const newDate = Array.isArray(value) ? value[0] : value;
                          if (newDate instanceof Date) {
                            setSelectedDate(newDate);
                          }
                        }}
                        tileDisabled={disabledDays}
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
                        <span className="font-medium">Valor:</span> R$ {typeof selectedService.preco === 'number' ? selectedService.preco.toFixed(2) : 'N/A'}
                      </p>
                    </div>
                  </div>
                )}

                <Button
                  type="submit"
                  className="w-full bg-gray-600 hover:bg-gray-700 text-white py-6 text-lg"
                >
                  Confirmar Agendamento
                </Button>
              </form>
            </CardContent>
          </Card>
        </div>
);

const Appointment = () => {
  // 1. Chamar TODOS os hooks incondicionalmente no topo.
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const { user, loading: authLoading } = useAuth();
  const { toast } = useToast();
  const [selectedService, setSelectedService] = useState<Servico | null>(null);
  const [selectedDate, setSelectedDate] = useState<Date | undefined>(undefined);
  const [selectedTime, setSelectedTime] = useState<string>('');

  const { data: barbershop, isLoading, error } = useQuery<BarberShop>({
    queryKey: ['barbershop', id],
    queryFn: async () => {
      const response = await estabelecimentoService.getById(Number(id));
      // Processa a resposta para garantir que os serviços sejam do tipo correto
      const processedServicos = response.servicos.map((servico, index) => {
        if (typeof servico === 'string') {
          // Tenta fazer o parse da string. Se falhar, retorna um objeto padrão.
          try {
            const tipoMatch = servico.match(/tipo=([^,]+)/);
            const descMatch = servico.match(/descricao=([^,]+)/);
            const precoMatch = servico.match(/preco=([^,]+)/);
            const duracaoMatch = servico.match(/duracaoMinutos=(\d+)/);
            return {
              id: index, // Usar index como fallback
              tipo: tipoMatch ? tipoMatch[1] : 'N/A',
              descricao: descMatch ? descMatch[1] : 'Serviço',
              preco: precoMatch ? parseFloat(precoMatch[1]) : 0,
              duracaoMinutos: duracaoMatch ? parseInt(duracaoMatch[1]) : 30,
            };
          } catch {
            return { id: index, tipo: 'N/A', descricao: 'Serviço Inválido', preco: 0, duracaoMinutos: 0 };
          }
        }
        return servico as Servico;
      });

      return { ...response, servicos: processedServicos };
    },
    enabled: !!id && !authLoading && !!user,
  });

  const { data: horarios, isLoading: isLoadingHorarios } = useQuery({
    queryKey: ['horarios', id],
    queryFn: () => estabelecimentoService.getHorarios(Number(id)),
    enabled: !!id && !authLoading && !!user,
  });

  const diasDeFuncionamento = React.useMemo(() => {
    if (!horarios) return [];
    console.log("--- DEBUG FRONTEND ---");
    console.log("1. Horarios recebidos do backend:", horarios); 
    const diaMap: { [key: string]: number } = {
      DOMINGO: 0,
      SEGUNDA: 1,
      TERCA: 2,
      QUARTA: 3,
      QUINTA: 4,
      SEXTA: 5,
      SABADO: 6,
    };
    const diasMapeados = horarios.map(h => diaMap[h.diaSemana]).filter(d => d !== undefined);
    console.log("2. Dias da semana mapeados (0=Dom, 1=Seg...):", diasMapeados);
    console.log("----------------------");
    return diasMapeados;
  }, [horarios]);

  const availableTimes = React.useMemo(() => {
    const times = [];
    for (let hour = 9; hour <= 18; hour++) {
      times.push(`${hour.toString().padStart(2, '0')}:00`);
      times.push(`${hour.toString().padStart(2, '0')}:30`);
    }
    return times;
  }, []);

  const disabledDays = ({ date }: { date: Date }): boolean => {
    const today = new Date();
    const futureLimit = new Date();
    futureLimit.setDate(today.getDate() + 30);

    today.setHours(0, 0, 0, 0);

    // Se a barbearia funciona em um dia da semana específico, a função retorna `false` (não desabilitado).
    // Se não funciona, retorna `true` (desabilitado).
    const isClosedOnThisDay = !diasDeFuncionamento.includes(date.getDay());

    return date < today || date > futureLimit || isClosedOnThisDay;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!user?.id) {
      toast({
        title: "Erro",
        description: "Usuário não autenticado",
        variant: "destructive",
      });
      return;
    }

    if (!selectedService || !selectedService.id) {
      toast({
        title: "Erro",
        description: "Por favor, selecione um serviço",
        variant: "destructive",
      });
      return;
    }

    if (!selectedDate) {
      toast({
        title: "Erro",
        description: "Por favor, selecione uma data",
        variant: "destructive",
      });
      return;
    }

    if (!selectedTime) {
      toast({
        title: "Erro",
        description: "Por favor, selecione um horário",
        variant: "destructive",
      });
      return;
    }

    try {
      const dataHora = new Date(selectedDate);
      const [hours, minutes] = selectedTime.split(':');
      dataHora.setHours(parseInt(hours), parseInt(minutes), 0, 0);

      const dataHoraLocal = new Date(dataHora.getTime() - dataHora.getTimezoneOffset() * 60000);

      if (dataHoraLocal <= new Date()) {
        toast({
          title: "Erro",
          description: "A data e hora devem ser futuras",
          variant: "destructive",
        });
        return;
      }

      const agendamentoData = {
        clienteId: user.id,
        estabelecimentoId: Number(id),
        servicos: [selectedService.id],
        dataHora: dataHoraLocal.toISOString()
      };

      await agendamentoService.criarAgendamento(agendamentoData);

      toast({
        title: "Sucesso",
        description: "Agendamento realizado com sucesso!",
        variant: "default",
      });
      navigate('/client/appointments');
    } catch (error: any) {
      console.error('Erro ao realizar agendamento:', error);
      toast({
        title: "Erro",
        description: error.response?.data?.message || 'Erro ao realizar agendamento',
        variant: "destructive",
      });
    }
  };

  // 2. AGORA, com todos os hooks já chamados, podemos fazer a renderização condicional.
  if (authLoading || isLoading || isLoadingHorarios) {
    return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center">
        <Spinner />
      </div>
    );
  }

  if (!user) {
    return (
      <div className="min-h-screen bg-gray-50">
        <Navigation />
        <AlertDialog open={true}>
          <AlertDialogContent>
            <AlertDialogHeader>
              <AlertDialogTitle>Login Necessário</AlertDialogTitle>
              <AlertDialogDescription>
                Você precisa estar logado para agendar um horário.
              </AlertDialogDescription>
            </AlertDialogHeader>
            <AlertDialogFooter>
              <AlertDialogCancel onClick={() => navigate(-1)}>Cancelar</AlertDialogCancel>
              <AlertDialogAction onClick={() => navigate('/login', { state: { from: { pathname: `/agendamento/${id}` } } })}>
                Fazer Login
              </AlertDialogAction>
            </AlertDialogFooter>
          </AlertDialogContent>
        </AlertDialog>
      </div>
    );
  }

  if (error || !barbershop) {
    return (
      <div className="min-h-screen bg-gray-50">
        <Navigation />
        <div className="container mx-auto px-4 py-8 mt-20 text-center">
            <h1 className="text-2xl font-bold text-red-600">Erro ao carregar detalhes da barbearia</h1>
            <p className="mt-2 text-gray-600">Por favor, tente novamente mais tarde.</p>
            <Button onClick={() => navigate('/barbershops')} className="mt-4">Voltar</Button>
        </div>
      </div>
    );
  }

  // 3. Renderização de sucesso.
  return (
    <div className="min-h-screen bg-gray-50">
      <Navigation />
      <div className="container mx-auto px-4 py-8 mt-20">
        <Button onClick={() => navigate(`/barbershops/${id}`)} variant="outline" className="mb-6">
          ← Voltar
        </Button>
        <AppointmentContent {...{ barbershop, availableTimes, handleSubmit, selectedService, setSelectedService, selectedDate, setSelectedDate, selectedTime, setSelectedTime, disabledDays }} />
      </div>
    </div>
  );
};

export default Appointment; 