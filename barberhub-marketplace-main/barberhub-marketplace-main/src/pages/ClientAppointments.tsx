import React from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useAuth } from '../contexts/AuthContext';
import { agendamentoService, Agendamento } from '../services/agendamento.service';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Spinner } from '../components/Spinner';
import { toast } from 'react-toastify';
import { FaCalendarAlt, FaClock, FaMapMarkerAlt, FaTrash } from 'react-icons/fa';
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

  const handleCancelar = async (id: number) => {
    if (window.confirm('Tem certeza que deseja cancelar este agendamento?')) {
      try {
        await cancelarAgendamento.mutateAsync(id);
      } catch (error) {
        console.error('Erro ao cancelar agendamento:', error);
      }
    }
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
              className="mt-4 bg-blue-600 hover:bg-blue-700"
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
              className="mt-4 bg-blue-600 hover:bg-blue-700"
            >
              Tentar Novamente
            </Button>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="container mx-auto px-4 py-8">
        <div className="flex justify-between items-center mb-8">
          <h1 className="text-3xl font-bold text-gray-800">Meus Agendamentos</h1>
          <Button
            onClick={() => navigate('/barbershops')}
            className="bg-blue-600 hover:bg-blue-700"
          >
            Novo Agendamento
          </Button>
        </div>

        {agendamentos && agendamentos.length > 0 ? (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {agendamentos.map((agendamento) => (
              <Card key={agendamento.id}>
                <CardHeader>
                  <CardTitle className="flex items-center justify-between">
                    <span className="flex items-center gap-2">
                      <FaCalendarAlt className="text-blue-600" />
                      {new Date(agendamento.data).toLocaleDateString('pt-BR')}
                    </span>
                    <span className={`px-2 py-1 rounded-full text-sm ${
                      agendamento.status === 'AGENDADO' ? 'bg-green-100 text-green-800' :
                      agendamento.status === 'CONCLUIDO' ? 'bg-blue-100 text-blue-800' :
                      'bg-red-100 text-red-800'
                    }`}>
                      {agendamento.status === 'AGENDADO' ? 'Agendado' :
                       agendamento.status === 'CONCLUIDO' ? 'Concluído' :
                       'Cancelado'}
                    </span>
                  </CardTitle>
                </CardHeader>
                <CardContent>
                  <div className="space-y-4">
                    <div>
                      <h3 className="font-medium text-gray-800">{agendamento.estabelecimento.nomeEstabelecimento}</h3>
                      <div className="flex items-center text-gray-600 mt-1">
                        <FaMapMarkerAlt className="mr-2" />
                        <span>{agendamento.estabelecimento.endereco}, {agendamento.estabelecimento.cidade}</span>
                      </div>
                    </div>

                    <div>
                      <h4 className="font-medium text-gray-800">Serviço</h4>
                      <p className="text-gray-600">{agendamento.servico.descricao}</p>
                      <p className="text-gray-600">R$ {agendamento.servico.preco.toFixed(2)}</p>
                    </div>

                    <div className="flex items-center text-gray-600">
                      <FaClock className="mr-2" />
                      <span>{agendamento.horario} ({agendamento.servico.duracaoMinutos} min)</span>
                    </div>

                    {agendamento.status === 'AGENDADO' && (
                      <Button
                        variant="destructive"
                        className="w-full"
                        onClick={() => handleCancelar(agendamento.id)}
                        disabled={cancelarAgendamento.isPending}
                      >
                        <FaTrash className="mr-2" />
                        Cancelar Agendamento
                      </Button>
                    )}
                  </div>
                </CardContent>
              </Card>
            ))}
          </div>
        ) : (
          <div className="text-center py-12">
            <h2 className="text-xl font-medium text-gray-600">Você não possui agendamentos</h2>
            <p className="mt-2 text-gray-500">Faça seu primeiro agendamento em uma de nossas barbearias parceiras!</p>
            <Button
              onClick={() => navigate('/barbershops')}
              className="mt-4 bg-blue-600 hover:bg-blue-700"
            >
              Ver Barbearias
            </Button>
          </div>
        )}
      </div>
    </div>
  );
};

export default ClientAppointments; 