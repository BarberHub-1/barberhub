import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import * as z from "zod";
import { Scissors, MapPin, User, Mail, Phone, Store, Clock, Image, Calendar, Upload, Edit2, Save, X } from "lucide-react";
import { useToast } from "@/hooks/use-toast";

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

const BarberProfile = () => {
  const { toast } = useToast();
  const navigate = useNavigate();
  const [isEditing, setIsEditing] = useState(false);
  
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

  function onSubmit(data: BarberProfileFormValues) {
    console.log(data);
    toast({
      title: "Perfil Atualizado",
      description: "Seu perfil foi atualizado com sucesso!",
    });
    setIsEditing(false);
  }

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
                  onClick={() => setIsEditing(true)}
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
            <h1 className="text-3xl font-bold text-barber-900">Perfil da Barbearia</h1>
            <p className="mt-2 text-barber-600">
              Gerencie as informações do seu perfil e mantenha seus clientes atualizados
            </p>
          </div>

          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
              <div className="grid md:grid-cols-2 gap-6">
                <FormField
                  control={form.control}
                  name="shopName"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Nome da Barbearia</FormLabel>
                      <div className="relative">
                        <Store className="absolute left-3 top-2.5 h-5 w-5 text-muted-foreground" />
                        <FormControl>
                          <Input 
                            placeholder="Nome da sua Barbearia" 
                            className="pl-10" 
                            {...field} 
                            disabled={!isEditing}
                          />
                        </FormControl>
                      </div>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                
                <FormField
                  control={form.control}
                  name="ownerName"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Nome do Proprietário</FormLabel>
                      <div className="relative">
                        <User className="absolute left-3 top-2.5 h-5 w-5 text-muted-foreground" />
                        <FormControl>
                          <Input 
                            placeholder="Nome Completo" 
                            className="pl-10" 
                            {...field} 
                            disabled={!isEditing}
                          />
                        </FormControl>
                      </div>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>

              <div className="grid md:grid-cols-2 gap-6">
                <FormField
                  control={form.control}
                  name="email"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Endereço de E-mail</FormLabel>
                      <div className="relative">
                        <Mail className="absolute left-3 top-2.5 h-5 w-5 text-muted-foreground" />
                        <FormControl>
                          <Input 
                            placeholder="seu@email.com" 
                            className="pl-10" 
                            {...field} 
                            disabled={!isEditing}
                          />
                        </FormControl>
                      </div>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                
                <FormField
                  control={form.control}
                  name="phone"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Número de Telefone</FormLabel>
                      <div className="relative">
                        <Phone className="absolute left-3 top-2.5 h-5 w-5 text-muted-foreground" />
                        <FormControl>
                          <Input 
                            placeholder="(55) 12345-6789" 
                            className="pl-10" 
                            {...field} 
                            disabled={!isEditing}
                          />
                        </FormControl>
                      </div>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>

              <div className="grid md:grid-cols-2 gap-6">
                <FormField
                  control={form.control}
                  name="address"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Endereço</FormLabel>
                      <div className="relative">
                        <MapPin className="absolute left-3 top-2.5 h-5 w-5 text-muted-foreground" />
                        <FormControl>
                          <Input 
                            placeholder="Rua Principal, 123" 
                            className="pl-10" 
                            {...field} 
                            disabled={!isEditing}
                          />
                        </FormControl>
                      </div>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                
                <FormField
                  control={form.control}
                  name="city"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Cidade</FormLabel>
                      <div className="relative">
                        <MapPin className="absolute left-3 top-2.5 h-5 w-5 text-muted-foreground" />
                        <FormControl>
                          <Input 
                            placeholder="São Paulo" 
                            className="pl-10" 
                            {...field} 
                            disabled={!isEditing}
                          />
                        </FormControl>
                      </div>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>

              <FormField
                control={form.control}
                name="description"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Descrição do Negócio</FormLabel>
                    <FormControl>
                      <Textarea 
                        placeholder="Conte-nos sobre sua barbearia, especialidades, experiência, etc."
                        className="min-h-[120px]"
                        {...field} 
                        disabled={!isEditing}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <div className="grid md:grid-cols-2 gap-6">
                <FormField
                  control={form.control}
                  name="services"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Serviços Oferecidos</FormLabel>
                      <FormControl>
                        <Textarea 
                          placeholder="Corte de Cabelo, Barba, Navalha, etc. (um por linha)"
                          className="min-h-[100px]"
                          {...field} 
                          disabled={!isEditing}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                
                <FormField
                  control={form.control}
                  name="workingHours"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Horário de Funcionamento</FormLabel>
                      <div className="relative">
                        <Clock className="absolute left-3 top-2.5 h-5 w-5 text-muted-foreground" />
                        <FormControl>
                          <Textarea 
                            placeholder="Seg-Sex: 9h-19h, Sáb: 10h-17h, etc."
                            className="pl-10 min-h-[100px]"
                            {...field} 
                            disabled={!isEditing}
                          />
                        </FormControl>
                      </div>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>

              <div className="p-4 border border-barber-200 rounded-lg bg-barber-50">
                <div className="flex items-center justify-center w-full">
                  <label className="flex flex-col items-center justify-center w-full h-32 border-2 border-barber-300 border-dashed rounded-lg cursor-pointer bg-barber-50 hover:bg-barber-100 transition-colors">
                    <div className="flex flex-col items-center justify-center pt-5 pb-6">
                      <Upload className="w-8 h-8 mb-1 text-barber-500" />
                      <p className="mb-2 text-sm text-barber-700">
                        <span className="font-semibold">Clique para enviar fotos</span> ou arraste e solte
                      </p>
                      <p className="text-xs text-barber-500">PNG, JPG ou WEBP (MÁX. 5MB cada)</p>
                    </div>
                    <input id="dropzone-file" type="file" className="hidden" multiple disabled={!isEditing} />
                  </label>
                </div>
                <p className="text-xs text-barber-500 mt-2 text-center">
                  Envie fotos da sua barbearia e trabalhos anteriores para mostrar suas habilidades
                </p>
              </div>
            </form>
          </Form>
        </div>
      </div>
    </div>
  );
};

export default BarberProfile; 