import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Calendar, Clock, MapPin, Scissors, Plus, Calendar as CalendarIcon } from "lucide-react";
import { useToast } from "@/hooks/use-toast";
import { format } from "date-fns";
import { ptBR } from "date-fns/locale";

type Appointment = {
  id: string;
  barberShop: string;
  service: string;
  date: Date;
  status: "scheduled" | "completed" | "cancelled";
  price: number;
};

const ClientAppointments = () => {
  const { toast } = useToast();
  const navigate = useNavigate();
  const [appointments] = useState<Appointment[]>([
    {
      id: "1",
      barberShop: "Barbearia do João",
      service: "Corte + Barba",
      date: new Date(2024, 3, 15, 14, 30),
      status: "scheduled",
      price: 50.00,
    },
    {
      id: "2",
      barberShop: "Barbearia do Pedro",
      service: "Corte",
      date: new Date(2024, 3, 10, 10, 0),
      status: "completed",
      price: 30.00,
    },
  ]);

  const getStatusBadge = (status: Appointment["status"]) => {
    const statusConfig = {
      scheduled: { label: "Agendado", variant: "default" },
      completed: { label: "Concluído", variant: "success" },
      cancelled: { label: "Cancelado", variant: "destructive" },
    };

    const config = statusConfig[status];
    return (
      <Badge variant={config.variant as any}>
        {config.label}
      </Badge>
    );
  };

  return (
    <div className="min-h-screen bg-barber-50 pt-24 pb-12">
      <div className="container mx-auto max-w-4xl px-4">
        <div className="flex justify-between items-center mb-8">
          <div>
            <h1 className="text-3xl font-bold text-barber-900">Meus Agendamentos</h1>
            <p className="mt-2 text-barber-600">
              Gerencie seus agendamentos e visualize seu histórico
            </p>
          </div>
          <Button 
            className="flex items-center gap-2 bg-barber-900 hover:bg-barber-800"
            onClick={() => navigate("/barbershops")}
          >
            <Plus size={16} />
            Novo Agendamento
          </Button>
        </div>

        <div className="space-y-4">
          {appointments.map((appointment) => (
            <Card key={appointment.id}>
              <CardHeader>
                <div className="flex justify-between items-start">
                  <div>
                    <CardTitle className="text-xl">{appointment.barberShop}</CardTitle>
                    <CardDescription className="mt-1">
                      <div className="flex items-center gap-2">
                        <Scissors size={16} />
                        {appointment.service}
                      </div>
                    </CardDescription>
                  </div>
                  {getStatusBadge(appointment.status)}
                </div>
              </CardHeader>
              <CardContent>
                <div className="grid md:grid-cols-2 gap-4">
                  <div className="space-y-2">
                    <div className="flex items-center gap-2 text-barber-600">
                      <Calendar size={16} />
                      <span>
                        {format(appointment.date, "EEEE, d 'de' MMMM 'de' yyyy", { locale: ptBR })}
                      </span>
                    </div>
                    <div className="flex items-center gap-2 text-barber-600">
                      <Clock size={16} />
                      <span>
                        {format(appointment.date, "HH:mm")}
                      </span>
                    </div>
                  </div>
                  <div className="space-y-2">
                    <div className="flex items-center gap-2 text-barber-600">
                      <MapPin size={16} />
                      <span>Rua das Flores, 123 - São Paulo</span>
                    </div>
                    <div className="text-right">
                      <span className="text-lg font-semibold text-barber-900">
                        R$ {appointment.price.toFixed(2)}
                      </span>
                    </div>
                  </div>
                </div>
                {appointment.status === "scheduled" && (
                  <div className="mt-4 flex justify-end gap-2">
                    <Button variant="outline" size="sm">
                      Reagendar
                    </Button>
                    <Button variant="destructive" size="sm">
                      Cancelar
                    </Button>
                  </div>
                )}
              </CardContent>
            </Card>
          ))}

          {appointments.length === 0 && (
            <Card>
              <CardContent className="py-12">
                <div className="text-center">
                  <CalendarIcon size={48} className="mx-auto text-barber-300 mb-4" />
                  <h3 className="text-lg font-semibold text-barber-900 mb-2">
                    Nenhum agendamento encontrado
                  </h3>
                  <p className="text-barber-600 mb-4">
                    Você ainda não possui agendamentos. Clique no botão abaixo para agendar um horário.
                  </p>
                  <Button 
                    className="flex items-center gap-2 bg-barber-900 hover:bg-barber-800"
                    onClick={() => navigate("/barbershops")}
                  >
                    <Plus size={16} />
                    Novo Agendamento
                  </Button>
                </div>
              </CardContent>
            </Card>
          )}
        </div>
      </div>
    </div>
  );
};

export default ClientAppointments; 