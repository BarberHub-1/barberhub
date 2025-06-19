import React from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useAuth } from '../contexts/AuthContext';
import { agendamentoService, Agendamento } from '../services/agendamento.service';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Spinner } from '../components/Spinner';
import { toast } from 'react-toastify';
import { FaCalendarAlt, FaClock, FaMapMarkerAlt, FaTrash, FaCheck } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';

const ClientAppointments = () => {
  const { user } = useAuth();
  const queryClient = useQueryClient();
  const navigate = useNavigate();

  const { data: agendamentos, isLoading, error } = useQuery<Agendamento[]>({
    queryKey: ['agendamentos'],
    queryFn: agendamentoService.getAgendamentosCliente,
    enabled: !!user,
    retry: 1
  });

  const cancelarAgendamento = useMutation({
    mutationFn: agendamentoService.cancelarAgendamento,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['agendamentos'] });
      toast.success('Agendamento cancelado com sucesso!');
    },
    onError: (error) => {
      console.error('Erro ao cancelar agendamento:', error);
      toast.error('Erro ao cancelar agendamento. Por favor, tente novamente.');
    }
  });

  const concluirAgendamento = useMutation({
    mutationFn: agendamentoService.concluirAgendamento,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['agendamentos'] });
      toast.success('Agendamento concluído com sucesso!');
    },
    onError: (error) => {
      console.error('Erro ao concluir agendamento:', error);
      toast.error('Erro ao concluir agendamento. Por favor, tente novamente.');
    }
  });

  const handleCancelar = async (id: number) => {
    if (window.confirm('Tem certeza que deseja cancelar este agendamento?')) {
      try {
        await cancelarAgendamento.mutateAsync(id);
      } catch (error) {
        console.error('Erro ao cancelar agendamento:', error);
      }
    }
  };

  const handleConcluir = async (id: number) => {
    if (window.confirm('Tem certeza que deseja concluir este agendamento?')) {
      try {
        await concluirAgendamento.mutateAsync(id);
      } catch (error) {
        console.error('Erro ao concluir agendamento:', error);
      }
    }
  };

  const formatarServico = (servico: string) => {
    const formatacoes: { [key: string]: string } = {
      'HIDRATACAO': 'Hidratação',
      'LUZES': 'Luzes',
      'CORTE': 'Corte',
      'BARBA': 'Barba',
      'COLORACAO': 'Coloração',
      'PINTURA': 'Pintura',
      'ALISAMENTO': 'Alisamento',
      'PENTEADO': 'Penteado',
      'MANICURE': 'Manicure',
      'PEDICURE': 'Pedicure',
      'MASSAGEM': 'Massagem',
      'LIMPEZA': 'Limpeza de Pele',
      'MAQUIAGEM': 'Maquiagem',
      'DEPILACAO': 'Depilação',
      'TRATAMENTO': 'Tratamento Capilar',
      'CORTE_DE_CABELO': 'Corte de Cabelo',
      'CORTE_DE_BARBA': 'Corte de Barba',
      'HIDRATACAO_CAPILAR': 'Hidratação Capilar',
      'COLORACAO_CAPILAR': 'Coloração Capilar',
      'PINTURA_CAPILAR': 'Pintura Capilar',
      'MAQUIAGEM_FESTA': 'Maquiagem para Festa',
      'DEPILACAO_FACIAL': 'Depilação Facial',
      'TRATAMENTO_CAPILAR': 'Tratamento Capilar',
      'CORTE_DE_CABELO_E_BARBA': 'Corte de Cabelo e Barba',
      'ALISAMENTO_CAPILAR': 'Alisamento Capilar',
    };
    return formatacoes[servico] || servico;
  };

  if (!user) {
    return (
      <div className="min-h-screen bg-gray-50">
        <div className="container mx-auto px-4 py-8">
          <div className="text-center">
            <h1 className="text-2xl font-bold text-red-600">Acesso Negado</h1>
            <p className="mt-2 text-gray-600">Você precisa estar logado para ver seus agendamentos.</p>
            <Button
              onClick={() => navigate('/login')}
              className="mt-4 bg-gray-600 hover:bg-gray-700"
            >
              Fazer Login
            </Button>
          </div>
        </div>
      </div>
    );
  }

  if (isLoading) {
    return (
      <div className="min-h-screen bg-gray-50">
        <div className="container mx-auto px-4 py-8">
          <div className="flex justify-center items-center min-h-[60vh]">
            <Spinner />
          </div>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="min-h-screen bg-gray-50">
        <div className="container mx-auto px-4 py-8">
          <div className="text-center">
            <h1 className="text-2xl font-bold text-red-600">Erro ao carregar agendamentos</h1>
            <p className="mt-2 text-gray-600">Por favor, tente novamente mais tarde.</p>
            <Button
              onClick={() => queryClient.invalidateQueries({ queryKey: ['agendamentos'] })}
              className="mt-4 bg-gray-600 hover:bg-gray-700"
            >
              Tentar Novamente
            </Button>
          </div>
        </div>
      </div>
    );
  }

  const agendamentosAtivos = agendamentos?.filter(
    agendamento => agendamento.status === 'AGENDADA'
  );

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="container mx-auto px-4 py-8">
        <div className="flex justify-between items-center mb-8">
          <h1 className="text-3xl font-bold text-gray-800">Meus Agendamentos</h1>
          <div className="flex gap-4">
            <Button
              onClick={() => navigate('/client/history')}
              className="bg-gray-600 hover:bg-gray-700"
            >
              Ver Histórico
            </Button>
          </div>
        </div>

        {agendamentosAtivos && agendamentosAtivos.length > 0 ? (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {agendamentosAtivos.map((agendamento) => (
              <Card key={agendamento.id}>
                <CardHeader>
                  <CardTitle className="flex items-center justify-between">
                    <span className="flex items-center gap-2">
                      <FaCalendarAlt className="text-gray-600" />
                      {new Date(agendamento.dataHora).toLocaleDateString('pt-BR')}
                    </span>
                    <span className="px-2 py-1 rounded-full text-sm bg-green-100 text-green-800">
                      Agendado
                    </span>
                  </CardTitle>
                </CardHeader>
                <CardContent>
                  <div className="space-y-4">
                    <div>
                      <h3 className="font-medium text-gray-800">Estabelecimento: {agendamento.estabelecimentoNome}</h3>
                    </div>

                    <div>
                      <h4 className="font-medium text-gray-800">Serviços:</h4>
                      <ul className="text-gray-600 list-disc list-inside">
                        {agendamento.servicosNomes.map((servico, idx) => (
                          <li key={idx}>{formatarServico(servico)}</li>
                        ))}
                      </ul>
                    </div>

                    <div className="flex items-center text-gray-600">
                      <FaClock className="mr-2" />
                      {new Date(agendamento.dataHora).toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' })}
                    </div>

                    <div className="flex gap-2">
                      <Button
                        onClick={() => handleCancelar(agendamento.id)}
                        className="bg-red-600 hover:bg-red-700"
                      >
                        Cancelar
                      </Button>
                      <Button
                        onClick={() => handleConcluir(agendamento.id)}
                        className="bg-gray-600 hover:bg-gray-700"
                      >
                        Concluir
                      </Button>
                    </div>
                  </div>
                </CardContent>
              </Card>
            ))}
          </div>
        ) : (
          <div className="text-center py-12">
            <h2 className="text-xl font-medium text-gray-600">Nenhum agendamento ativo</h2>
            <p className="mt-2 text-gray-500">Você não tem agendamentos ativos no momento.</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default ClientAppointments; 