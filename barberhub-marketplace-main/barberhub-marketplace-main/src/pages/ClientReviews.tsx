import React from 'react';
import { useQuery } from '@tanstack/react-query';
import { useAuth } from '../contexts/AuthContext';
import { agendamentoService } from '../services/agendamento.service';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Spinner } from '../components/Spinner';
import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';
import { Star } from 'lucide-react';

const ClientReviews: React.FC = () => {
  const { user } = useAuth();

  const { data: agendamentos, isLoading } = useQuery({
    queryKey: ['agendamentos'],
    queryFn: agendamentoService.getAgendamentosCliente,
    enabled: !!user
  });

  console.log('Agendamentos recebidos:', agendamentos);
  const avaliacoes = agendamentos?.filter(agendamento => {
    console.log('Agendamento:', agendamento);
    console.log('Avaliação:', agendamento.avaliacao);
    return agendamento.avaliacao;
  }) || [];
  console.log('Avaliações filtradas:', avaliacoes);

  if (isLoading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <Spinner />
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold text-gray-900">Minhas Avaliações</h1>
      </div>

      {avaliacoes.length === 0 ? (
        <Card>
          <CardContent className="p-6">
            <p className="text-center text-gray-500">
              Você ainda não realizou nenhuma avaliação.
            </p>
          </CardContent>
        </Card>
      ) : (
        <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
          {avaliacoes.map((agendamento) => (
            <Card key={agendamento.id} className="hover:shadow-lg transition-shadow">
              <CardHeader>
                <CardTitle className="text-xl">
                  {agendamento.estabelecimentoNome}
                </CardTitle>
              </CardHeader>
              <CardContent>
                <div className="space-y-4">
                  <div className="flex items-center">
                    {[...Array(5)].map((_, index) => (
                      <Star
                        key={index}
                        className={`w-5 h-5 ${
                          index < (agendamento.avaliacao?.nota || 0)
                            ? 'text-yellow-400 fill-yellow-400'
                            : 'text-gray-300'
                        }`}
                      />
                    ))}
                  </div>
                  <p className="text-gray-600">
                    {agendamento.avaliacao?.comentario}
                  </p>
                  <div className="text-sm text-gray-500">
                    <p>
                      Data do agendamento:{' '}
                      {format(new Date(agendamento.dataHora), "dd 'de' MMMM 'de' yyyy", {
                        locale: ptBR,
                      })}
                    </p>
                    <p>
                      Data da avaliação:{' '}
                      {format(
                        new Date(agendamento.avaliacao?.dataAvaliacao || ''),
                        "dd 'de' MMMM 'de' yyyy",
                        { locale: ptBR }
                      )}
                    </p>
                  </div>
                  <div className="text-sm">
                    <p className="font-medium">Serviços realizados:</p>
                    <ul className="list-disc list-inside text-gray-600">
                      {agendamento.servicosNomes.map((servico, index) => (
                        <li key={index}>{servico}</li>
                      ))}
                    </ul>
                  </div>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      )}
    </div>
  );
};

export default ClientReviews; 