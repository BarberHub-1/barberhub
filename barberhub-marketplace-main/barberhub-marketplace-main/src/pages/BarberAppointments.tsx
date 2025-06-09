import { useState } from "react";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { ScrollArea } from "@/components/ui/scroll-area";
import { useToast } from "@/hooks/use-toast";
import { Calendar, Clock, User, X, Check, AlertCircle } from "lucide-react";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog";
import BarberCalendar from "@/components/BarberCalendar";

interface Appointment {
  id: string;
  clientName: string;
  services: string[];
  date: string;
  time: string;
  status: "current" | "upcoming" | "completed" | "cancelled";
  employee?: string;
}

const BarberAppointments = () => {
  const { toast } = useToast();
  const [appointments, setAppointments] = useState<Appointment[]>([
    {
      id: "1",
      clientName: "João Silva",
      services: ["Corte de Cabelo", "Barba"],
      date: "2024-03-20",
      time: "14:00",
      status: "current",
      employee: "Carlos"
    },
    {
      id: "2",
      clientName: "Maria Santos",
      services: ["Corte de Cabelo", "Luzes"],
      date: "2024-03-20",
      time: "15:30",
      status: "upcoming",
      employee: "Ana"
    },
    {
      id: "3",
      clientName: "Pedro Oliveira",
      services: ["Barba"],
      date: "2024-03-19",
      time: "10:00",
      status: "completed",
      employee: "Carlos"
    }
  ]);

  const [selectedAppointment, setSelectedAppointment] = useState<Appointment | null>(null);
  const [showCancelDialog, setShowCancelDialog] = useState(false);

  const handleCancelAppointment = (appointment: Appointment) => {
    setSelectedAppointment(appointment);
    setShowCancelDialog(true);
  };

  const confirmCancelAppointment = () => {
    if (selectedAppointment) {
      setAppointments(appointments.map(apt => 
        apt.id === selectedAppointment.id 
          ? { ...apt, status: "cancelled" }
          : apt
      ));
      
      toast({
        title: "Agendamento cancelado",
        description: "O agendamento foi cancelado com sucesso.",
      });
    }
    setShowCancelDialog(false);
  };

  const handleCompleteAppointment = (appointment: Appointment) => {
    setAppointments(appointments.map(apt => 
      apt.id === appointment.id 
        ? { ...apt, status: "completed" }
        : apt
    ));
    
    toast({
      title: "Agendamento finalizado",
      description: "O atendimento foi marcado como concluído.",
    });
  };

  const getStatusBadge = (status: Appointment["status"]) => {
    switch (status) {
      case "current":
        return <Badge className="bg-gray-500">Em andamento</Badge>;
      case "upcoming":
        return <Badge className="bg-green-500">Agendado</Badge>;
      case "completed":
        return <Badge className="bg-gray-500">Finalizado</Badge>;
      case "cancelled":
        return <Badge className="bg-red-500">Cancelado</Badge>;
    }
  };

  const AppointmentCard = ({ appointment }: { appointment: Appointment }) => (
    <Card className="mb-4">
      <CardContent className="p-4">
        <div className="flex justify-between items-start mb-4">
          <div>
            <h3 className="font-semibold text-lg">{appointment.clientName}</h3>
            {getStatusBadge(appointment.status)}
          </div>
          {appointment.employee && (
            <div className="flex items-center text-sm text-gray-600">
              <User className="w-4 h-4 mr-1" />
              {appointment.employee}
            </div>
          )}
        </div>
        
        <div className="space-y-2">
          <div className="flex items-center text-sm text-gray-600">
            <Calendar className="w-4 h-4 mr-2" />
            {new Date(appointment.date).toLocaleDateString('pt-BR')}
          </div>
          <div className="flex items-center text-sm text-gray-600">
            <Clock className="w-4 h-4 mr-2" />
            {appointment.time}
          </div>
          <div className="flex flex-wrap gap-2 mt-2">
            {appointment.services.map((service, index) => (
              <Badge key={index} variant="secondary">
                {service}
              </Badge>
            ))}
          </div>
        </div>

        <div className="flex justify-end gap-2 mt-4">
          {appointment.status === "current" && (
            <Button
              variant="outline"
              size="sm"
              onClick={() => handleCompleteAppointment(appointment)}
            >
              <Check className="w-4 h-4 mr-2" />
              Finalizar
            </Button>
          )}
          {appointment.status !== "cancelled" && appointment.status !== "completed" && (
            <Button
              variant="destructive"
              size="sm"
              onClick={() => handleCancelAppointment(appointment)}
            >
              <X className="w-4 h-4 mr-2" />
              Cancelar
            </Button>
          )}
        </div>
      </CardContent>
    </Card>
  );

  return (
    <div className="container mx-auto py-8">
      <div className="flex justify-between items-center mb-8">
        <h1 className="text-3xl font-bold">Gerenciamento de Agendamentos</h1>
        <BarberCalendar />
      </div>

      <Tabs defaultValue="current" className="space-y-4">
        <TabsList>
          <TabsTrigger value="current">Atendimentos Atuais</TabsTrigger>
          <TabsTrigger value="upcoming">Próximos Atendimentos</TabsTrigger>
          <TabsTrigger value="completed">Atendimentos Finalizados</TabsTrigger>
        </TabsList>

        <TabsContent value="current">
          <ScrollArea className="h-[600px] pr-4">
            {appointments
              .filter(apt => apt.status === "current")
              .map(appointment => (
                <AppointmentCard key={appointment.id} appointment={appointment} />
              ))}
          </ScrollArea>
        </TabsContent>

        <TabsContent value="upcoming">
          <ScrollArea className="h-[600px] pr-4">
            {appointments
              .filter(apt => apt.status === "upcoming")
              .map(appointment => (
                <AppointmentCard key={appointment.id} appointment={appointment} />
              ))}
          </ScrollArea>
        </TabsContent>

        <TabsContent value="completed">
          <ScrollArea className="h-[600px] pr-4">
            {appointments
              .filter(apt => apt.status === "completed" || apt.status === "cancelled")
              .map(appointment => (
                <AppointmentCard key={appointment.id} appointment={appointment} />
              ))}
          </ScrollArea>
        </TabsContent>
      </Tabs>

      <AlertDialog open={showCancelDialog} onOpenChange={setShowCancelDialog}>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Cancelar Agendamento</AlertDialogTitle>
            <AlertDialogDescription>
              Tem certeza que deseja cancelar este agendamento? Esta ação não pode ser desfeita.
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel>Voltar</AlertDialogCancel>
            <AlertDialogAction onClick={confirmCancelAppointment}>
              Confirmar Cancelamento
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </div>
  );
};

export default BarberAppointments; 