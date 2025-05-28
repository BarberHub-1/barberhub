import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Calendar, Clock, MapPin, Scissors, Star } from "lucide-react";
import { useToast } from "@/hooks/use-toast";
import { format } from "date-fns";
import { ptBR } from "date-fns/locale";

type Appointment = {
  id: string;
  barberShop: string;
  service: string;
  date: Date;
  status: "completed" | "cancelled";
  price: number;
  rating?: number;
};

const ClientHistory = () => {
  const { toast } = useToast();
  const [appointments, setAppointments] = useState<Appointment[]>([]);

  const getStatusBadge = (status: Appointment["status"]) => {
    const statusConfig = {
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

  const renderRating = (rating?: number) => {
    if (!rating) return null;

    return (
      <div className="flex items-center gap-1">
        {Array.from({ length: 5 }).map((_, index) => (
          <Star
            key={index}
            size={16}
            className={index < rating ? "text-yellow-400 fill-yellow-400" : "text-gray-300"}
          />
        ))}
      </div>
    );
  };

  return (
    <div className="min-h-screen bg-barber-50 pt-24 pb-12">
      <div className="container mx-auto max-w-4xl px-4">
        <div className="mb-8">
          <h1 className="text-3xl font-bold text-barber-900">Histórico de Agendamentos</h1>
          <p className="mt-2 text-barber-600">
            Visualize todos os seus agendamentos passados
          </p>
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
                {appointment.status === "completed" && (
                  <div className="mt-4">
                    <div className="flex items-center justify-between">
                      <span className="text-sm text-barber-600">Sua avaliação:</span>
                      {renderRating(appointment.rating)}
                    </div>
                  </div>
                )}
              </CardContent>
            </Card>
          ))}

          {appointments.length === 0 && (
            <Card>
              <CardContent className="py-12">
                <div className="text-center">
                  <Calendar size={48} className="mx-auto text-barber-300 mb-4" />
                  <h3 className="text-lg font-semibold text-barber-900 mb-2">
                    Nenhum histórico encontrado
                  </h3>
                  <p className="text-barber-600">
                    Você ainda não possui agendamentos no histórico.
                  </p>
                </div>
              </CardContent>
            </Card>
          )}
        </div>
      </div>
    </div>
  );
};

export default ClientHistory; 