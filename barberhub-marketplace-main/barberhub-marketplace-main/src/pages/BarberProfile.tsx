import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import * as z from "zod";
import { Scissors, Edit2, Save, X, Plus, Trash2 } from "lucide-react";
import { useToast } from "@/hooks/use-toast";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Label } from "@/components/ui/label";
import { Switch } from "@/components/ui/switch";
import { ScrollArea } from "@/components/ui/scroll-area";
import { AVAILABLE_SERVICES } from "@/constants/services";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

const barberProfileSchema = z.object({
  shopName: z.string().min(2, { message: "O nome da barbearia deve ter pelo menos 2 caracteres" }),
  ownerName: z.string().min(2, { message: "O nome do proprietário deve ter pelo menos 2 caracteres" }),
  email: z.string().email({ message: "Por favor, insira um endereço de e-mail válido" }),
  phone: z.string().min(10, { message: "Por favor, insira um número de telefone válido" }),
  address: z.string().min(5, { message: "O endereço deve ter pelo menos 5 caracteres" }),
  city: z.string().min(2, { message: "A cidade deve ter pelo menos 2 caracteres" }),
  description: z.string().min(20, { message: "A descrição deve ter pelo menos 20 caracteres" }),
  services: z.string().min(5, { message: "Por favor, insira pelo menos um serviço" }),
  workingHours: z.string().min(5, { message: "Por favor, insira seus horários de funcionamento" }),
});

type BarberProfileFormValues = z.infer<typeof barberProfileSchema>;

interface WorkingHours {
  day: string;
  isOpen: boolean;
  openTime: string;
  closeTime: string;
}

interface Service {
  id: string;
  name: string;
  price: number;
  duration: number;
}

const BarberProfile = () => {
  const { toast } = useToast();
  const navigate = useNavigate();
  const [isEditing, setIsEditing] = useState(false);
  const [shopImage] = useState<string>("https://placehold.co/400x300/e2e8f0/64748b?text=Foto+da+Barbearia");
  
  const form = useForm<BarberProfileFormValues>({
    resolver: zodResolver(barberProfileSchema),
    defaultValues: {
      shopName: "Barbearia do João",
      ownerName: "João Silva",
      email: "joao@barbearia.com",
      phone: "(11) 98765-4321",
      address: "Rua das Flores, 123",
      city: "São Paulo",
      description: "Barbearia tradicional com mais de 10 anos de experiência. Especializada em cortes modernos e barba.",
      services: "Corte de Cabelo\nBarba\nNavalha\nHidratação",
      workingHours: "Seg-Sex: 9h-19h\nSáb: 10h-17h\nDom: Fechado",
    },
  });

  const [workingHours, setWorkingHours] = useState<WorkingHours[]>([
    { day: "Segunda", isOpen: true, openTime: "09:00", closeTime: "18:00" },
    { day: "Terça", isOpen: true, openTime: "09:00", closeTime: "18:00" },
    { day: "Quarta", isOpen: true, openTime: "09:00", closeTime: "18:00" },
    { day: "Quinta", isOpen: true, openTime: "09:00", closeTime: "18:00" },
    { day: "Sexta", isOpen: true, openTime: "09:00", closeTime: "18:00" },
    { day: "Sábado", isOpen: true, openTime: "09:00", closeTime: "18:00" },
    { day: "Domingo", isOpen: false, openTime: "09:00", closeTime: "18:00" },
  ]);

  const [services, setServices] = useState<Service[]>([
    { id: "1", name: "Corte de Cabelo", price: 50, duration: 30 },
    { id: "2", name: "Barba", price: 30, duration: 20 },
  ]);

  const [showAddService, setShowAddService] = useState(false);
  const [newService, setNewService] = useState<Partial<Service>>({});

  function onSubmit(data: BarberProfileFormValues) {
    console.log(data);
    toast({
      title: "Perfil Atualizado",
      description: "Seu perfil foi atualizado com sucesso!",
    });
    setIsEditing(false);
  }

  const handleWorkingHoursChange = (day: string, field: keyof WorkingHours, value: string | boolean) => {
    setWorkingHours(workingHours.map(wh => 
      wh.day === day ? { ...wh, [field]: value } : wh
    ));
  };

  const handleAddService = () => {
    if (newService.name && newService.price && newService.duration) {
      setServices([...services, { id: Date.now().toString(), ...newService as Service }]);
      setNewService({});
      setShowAddService(false);
      toast({
        title: "Serviço adicionado",
        description: "O novo serviço foi adicionado com sucesso.",
      });
    }
  };

  const handleDeleteService = (serviceId: string) => {
    setServices(services.filter(s => s.id !== serviceId));
    toast({
      title: "Serviço removido",
      description: "O serviço foi removido com sucesso.",
    });
  };

  return (
    <div className="min-h-screen bg-barber-50 pt-24 pb-12">
      <div className="container mx-auto max-w-3xl px-4">
        <div className="bg-white rounded-xl shadow-lg p-8">
          <div className="flex justify-between items-center mb-8">
            <div className="flex items-center gap-2">
              <Scissors size={28} className="text-barber-900" />
              <span className="text-xl font-semibold tracking-tight text-barber-900">BarberHub</span>
            </div>
            <div className="flex gap-2">
              {!isEditing ? (
                <Button 
                  variant="outline" 
                  className="flex items-center gap-2"
                  onClick={() => navigate("/barber/edit-profile")}
                >
                  <Edit2 size={16} />
                  Editar Perfil
                </Button>
              ) : (
                <>
                  <Button 
                    variant="outline" 
                    className="flex items-center gap-2"
                    onClick={() => setIsEditing(false)}
                  >
                    <X size={16} />
                    Cancelar
                  </Button>
                  <Button 
                    className="flex items-center gap-2 bg-barber-900 hover:bg-barber-800"
                    onClick={form.handleSubmit(onSubmit)}
                  >
                    <Save size={16} />
                    Salvar Alterações
                  </Button>
                </>
              )}
            </div>
          </div>

          <div className="text-center mb-8">
            <div className="relative w-full max-w-md mx-auto aspect-[4/3] rounded-lg overflow-hidden border-2 border-barber-200 mb-6">
              <img
                src={shopImage}
                alt="Foto da Barbearia"
                className="w-full h-full object-cover"
              />
            </div>
            <h1 className="text-3xl font-bold text-barber-900">Perfil da Barbearia</h1>
            <p className="mt-2 text-barber-600">
              Gerencie as informações do seu perfil e mantenha seus clientes atualizados
            </p>
          </div>

          <Tabs defaultValue="hours" className="space-y-4">
            <TabsList>
              <TabsTrigger value="hours">Horários de Funcionamento</TabsTrigger>
              <TabsTrigger value="services">Serviços</TabsTrigger>
            </TabsList>

            <TabsContent value="hours">
              <Card>
                <CardHeader>
                  <CardTitle>Horários de Funcionamento</CardTitle>
                </CardHeader>
                <CardContent>
                  <div className="space-y-4">
                    {workingHours.map((wh) => (
                      <div key={wh.day} className="flex items-center justify-between p-4 border rounded-lg">
                        <div className="flex items-center space-x-4">
                          <Label className="w-24">{wh.day}</Label>
                          <Switch
                            checked={wh.isOpen}
                            onCheckedChange={(checked) => handleWorkingHoursChange(wh.day, "isOpen", checked)}
                          />
                        </div>
                        {wh.isOpen && (
                          <div className="flex items-center space-x-4">
                            <div className="flex items-center space-x-2">
                              <Label>Abertura</Label>
                              <Input
                                type="time"
                                value={wh.openTime}
                                onChange={(e) => handleWorkingHoursChange(wh.day, "openTime", e.target.value)}
                                className="w-32"
                              />
                            </div>
                            <div className="flex items-center space-x-2">
                              <Label>Fechamento</Label>
                              <Input
                                type="time"
                                value={wh.closeTime}
                                onChange={(e) => handleWorkingHoursChange(wh.day, "closeTime", e.target.value)}
                                className="w-32"
                              />
                            </div>
                          </div>
                        )}
                      </div>
                    ))}
                  </div>
                </CardContent>
              </Card>
            </TabsContent>

            <TabsContent value="services">
              <Card>
                <CardHeader className="flex flex-row items-center justify-between">
                  <CardTitle>Serviços Oferecidos</CardTitle>
                  <Dialog open={showAddService} onOpenChange={setShowAddService}>
                    <DialogTrigger asChild>
                      <Button>
                        <Plus className="w-4 h-4 mr-2" />
                        Adicionar Serviço
                      </Button>
                    </DialogTrigger>
                    <DialogContent>
                      <DialogHeader>
                        <DialogTitle>Adicionar Novo Serviço</DialogTitle>
                      </DialogHeader>
                      <div className="space-y-4 py-4">
                        <div className="space-y-2">
                          <Label>Serviço</Label>
                          <Select
                            onValueChange={(value) => setNewService({ ...newService, name: value })}
                          >
                            <SelectTrigger>
                              <SelectValue placeholder="Selecione um serviço" />
                            </SelectTrigger>
                            <SelectContent>
                              {AVAILABLE_SERVICES.map((service) => (
                                <SelectItem key={service.id} value={service.label}>
                                  {service.label}
                                </SelectItem>
                              ))}
                            </SelectContent>
                          </Select>
                        </div>
                        <div className="space-y-2">
                          <Label>Preço (R$)</Label>
                          <Input
                            type="number"
                            placeholder="0.00"
                            onChange={(e) => setNewService({ ...newService, price: Number(e.target.value) })}
                          />
                        </div>
                        <div className="space-y-2">
                          <Label>Duração (minutos)</Label>
                          <Input
                            type="number"
                            placeholder="30"
                            onChange={(e) => setNewService({ ...newService, duration: Number(e.target.value) })}
                          />
                        </div>
                      </div>
                      <DialogFooter>
                        <Button onClick={handleAddService}>Adicionar</Button>
                      </DialogFooter>
                    </DialogContent>
                  </Dialog>
                </CardHeader>
                <CardContent>
                  <ScrollArea className="h-[400px]">
                    <div className="space-y-4">
                      {services.map((service) => (
                        <div key={service.id} className="flex items-center justify-between p-4 border rounded-lg">
                          <div>
                            <h3 className="font-semibold">{service.name}</h3>
                            <p className="text-sm text-gray-600">
                              R$ {service.price.toFixed(2)} • {service.duration} minutos
                            </p>
                          </div>
                          <Button
                            variant="destructive"
                            size="sm"
                            onClick={() => handleDeleteService(service.id)}
                          >
                            <Trash2 className="w-4 h-4" />
                          </Button>
                        </div>
                      ))}
                    </div>
                  </ScrollArea>
                </CardContent>
              </Card>
            </TabsContent>
          </Tabs>
        </div>
      </div>
    </div>
  );
};

export default BarberProfile;