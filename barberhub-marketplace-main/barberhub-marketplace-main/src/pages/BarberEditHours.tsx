import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from "@/components/ui/card";
import { Label } from "@/components/ui/label";
import { Switch } from "@/components/ui/switch";
import { Input } from "@/components/ui/input";
import { useToast } from "@/hooks/use-toast";
import { useAuth } from "@/contexts/AuthContext";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import api from "@/lib/axios";
import { Clock, Save, X } from "lucide-react";

interface WorkingHours {
  id?: number;
  diaSemana: string;
  horarioAbertura: string;
  horarioFechamento: string;
  estabelecimentoId: number;
}

interface Estabelecimento {
  id: number;
  nomeProprietario: string;
  nomeEstabelecimento: string;
  cnpj: string;
  endereco: string;
  cidade: string;
  cep: string;
  telefone: string;
  email: string;
  status: string;
  descricao?: string;
  horario: WorkingHours[];
}

const DIAS_SEMANA = [
  "DOMINGO",
  "SEGUNDA",
  "TERCA",
  "QUARTA",
  "QUINTA",
  "SEXTA",
  "SABADO"
];

const BarberEditHours = () => {
  const { toast } = useToast();
  const navigate = useNavigate();
  const { user } = useAuth();
  const queryClient = useQueryClient();
  const [workingHours, setWorkingHours] = useState<WorkingHours[]>([]);

  const { data: establishmentData } = useQuery<Estabelecimento>({
    queryKey: ['establishment', user?.id],
    queryFn: async () => {
      if (!user?.id) throw new Error("ID do estabelecimento não encontrado");
      const response = await api.get<Estabelecimento>(`/api/estabelecimentos/${user.id}`);
      return response.data;
    },
    enabled: !!user?.id,
  });

  useEffect(() => {
    if (establishmentData?.horario) {
      setWorkingHours(establishmentData.horario);
    } else {
      // Inicializa com horários padrão se não existir
      const defaultHours = DIAS_SEMANA.map(dia => ({
        diaSemana: dia,
        horarioAbertura: "09:00",
        horarioFechamento: "18:00",
        estabelecimentoId: user?.id || 0
      }));
      setWorkingHours(defaultHours);
    }
  }, [establishmentData, user?.id]);

  const updateMutation = useMutation({
    mutationFn: async (hours: WorkingHours[]) => {
      if (!user?.id) throw new Error("ID do estabelecimento não encontrado");
      
      // Busca os dados atuais do estabelecimento
      const estabelecimentoAtual = await api.get<Estabelecimento>(`/api/estabelecimentos/${user.id}`);
      
      const formData = new FormData();
      const estabelecimentoData = {
        id: estabelecimentoAtual.data.id,
        nomeProprietario: estabelecimentoAtual.data.nomeProprietario,
        nomeEstabelecimento: estabelecimentoAtual.data.nomeEstabelecimento,
        cnpj: estabelecimentoAtual.data.cnpj,
        endereco: estabelecimentoAtual.data.endereco,
        cidade: estabelecimentoAtual.data.cidade,
        cep: estabelecimentoAtual.data.cep,
        telefone: estabelecimentoAtual.data.telefone,
        email: estabelecimentoAtual.data.email,
        status: estabelecimentoAtual.data.status,
        descricao: estabelecimentoAtual.data.descricao,
        horario: hours
      };
      
      formData.append('estabelecimento', new Blob([JSON.stringify(estabelecimentoData)], {
        type: 'application/json'
      }));

      const response = await api.put(`/api/estabelecimentos/${user.id}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });
      return response.data;
    },
    onSuccess: () => {
      toast({
        title: "Sucesso",
        description: "Horários atualizados com sucesso!"
      });
      queryClient.invalidateQueries({ queryKey: ["establishment", user?.id] });
      navigate("/barber/profile");
    },
    onError: (error) => {
      console.error("Erro ao atualizar horários:", error);
      toast({
        title: "Erro",
        description: "Erro ao atualizar horários. Verifique se você está autenticado e tente novamente.",
        variant: "destructive"
      });
    }
  });

  const handleTimeChange = (dia: string, field: 'horarioAbertura' | 'horarioFechamento', value: string) => {
    setWorkingHours(prev => 
      prev.map(wh => 
        wh.diaSemana === dia ? { ...wh, [field]: value } : wh
      )
    );
  };

  const handleSave = () => {
    updateMutation.mutate(workingHours);
  };

  return (
    <div className="min-h-screen bg-barber-50 pt-24 pb-12">
      <div className="container mx-auto max-w-3xl px-4">
        <div className="bg-white rounded-xl shadow-lg p-8">
          <div className="flex justify-between items-center mb-8">
            <div>
              <h1 className="text-3xl font-bold text-barber-900">Editar Horários</h1>
              <p className="mt-2 text-barber-600">
                Configure os horários de funcionamento da sua barbearia
              </p>
            </div>
            <div className="flex gap-2">
              <Button 
                variant="outline" 
                className="flex items-center gap-2"
                onClick={() => navigate("/barber/profile")}
              >
                <X size={16} />
                Cancelar
              </Button>
              <Button 
                className="flex items-center gap-2 bg-barber-900 hover:bg-barber-800"
                onClick={handleSave}
                disabled={updateMutation.isPending}
              >
                <Save size={16} />
                Salvar Alterações
              </Button>
            </div>
          </div>

          <Card>
            <CardHeader>
              <CardTitle>Horários de Funcionamento</CardTitle>
              <CardDescription>Defina os horários de atendimento para cada dia da semana</CardDescription>
            </CardHeader>
            <CardContent className="space-y-6">
              {workingHours.map((horario) => (
                <div key={horario.diaSemana} className="flex items-center gap-4 p-4 border rounded-lg">
                  <div className="w-32">
                    <Label>{horario.diaSemana}</Label>
                  </div>
                  <div className="flex items-center gap-4">
                    <div className="flex items-center gap-2">
                      <Clock size={16} className="text-barber-600" />
                      <Input
                        type="time"
                        value={horario.horarioAbertura}
                        onChange={(e) => handleTimeChange(horario.diaSemana, 'horarioAbertura', e.target.value)}
                        className="w-32"
                      />
                    </div>
                    <span className="text-barber-600">até</span>
                    <div className="flex items-center gap-2">
                      <Clock size={16} className="text-barber-600" />
                      <Input
                        type="time"
                        value={horario.horarioFechamento}
                        onChange={(e) => handleTimeChange(horario.diaSemana, 'horarioFechamento', e.target.value)}
                        className="w-32"
                      />
                    </div>
                  </div>
                </div>
              ))}
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  );
};

export default BarberEditHours; 