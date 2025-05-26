import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { Checkbox } from "@/components/ui/checkbox";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import * as z from "zod";
import { Scissors, MapPin, User, Mail, Phone, Store, Clock, Image, Calendar, Upload } from "lucide-react";
import { useToast } from "@/hooks/use-toast";

const barberShopSchema = z.object({
  shopName: z.string().min(2, { message: "O nome da barbearia deve ter pelo menos 2 caracteres" }),
  ownerName: z.string().min(2, { message: "O nome do proprietário deve ter pelo menos 2 caracteres" }),
  email: z.string().email({ message: "Por favor, insira um endereço de e-mail válido" }),
  phone: z.string().min(10, { message: "Por favor, insira um número de telefone válido" }),
  address: z.string().min(5, { message: "O endereço deve ter pelo menos 5 caracteres" }),
  city: z.string().min(2, { message: "A cidade deve ter pelo menos 2 caracteres" }),
  description: z.string().min(20, { message: "A descrição deve ter pelo menos 20 caracteres" }),
  services: z.string().min(5, { message: "Por favor, insira pelo menos um serviço" }),
  workingHours: z.string().min(5, { message: "Por favor, insira seus horários de funcionamento" }),
  termsAndConditions: z.literal(true, {
    errorMap: () => ({ message: "Você deve aceitar os termos e condições" }),
  }),
});

type BarberShopFormValues = z.infer<typeof barberShopSchema>;

const BarberSignup = () => {
  const { toast } = useToast();
  const navigate = useNavigate();
  
  const form = useForm<BarberShopFormValues>({
    resolver: zodResolver(barberShopSchema),
    defaultValues: {
      shopName: "",
      ownerName: "",
      email: "",
      phone: "",
      address: "",
      city: "",
      description: "",
      services: "",
      workingHours: "",
      termsAndConditions: undefined,
    },
  });

  // function onSubmit(data: BarberShopFormValues) {
  //   console.log(data);
  //   toast({
  //     title: "Inscrição Enviada",
  //     description: "Obrigado por se inscrever! Analisaremos suas informações e entraremos em contato em breve.",
  //   });
    
  //   // Redireciona para a página de barbeiros após o envio bem-sucedido
  //   setTimeout(() => navigate("/barbers"), 2000);
  // }

  function onSubmit(data: BarberShopFormValues) {
    fetch("http:///inscricao", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    })
      .then(response => {
        if (!response.ok) {
          throw new Error("Erro ao enviar inscrição");
        }
        return response.json();
      })
      .then(result => {
        toast({
          title: "Inscrição Enviada",
          description: "Obrigado por se inscrever! Analisaremos suas informações e entraremos em contato em breve.",
        });
        
        setTimeout(() => navigate("/barbers"), 2000);
      })
      .catch(error => {
        toast({
          title: "Erro",
          description: "Não foi possível enviar sua inscrição. Tente novamente mais tarde.",
        });
      });
  }

  return (
    <div className="min-h-screen bg-barber-50 pt-24 pb-12">
      <div className="container mx-auto max-w-3xl px-4">
        <div className="bg-white rounded-xl shadow-lg p-8">
          <div className="text-center mb-8">
            <Link to="/" className="inline-flex items-center gap-2 mb-6">
              <Scissors size={28} className="text-barber-900" />
              <span className="text-xl font-semibold tracking-tight text-barber-900">BarberHub</span>
            </Link>
            <h1 className="text-3xl font-bold text-barber-900">Inscreva-se no BarberHub</h1>
            <p className="mt-2 text-barber-600">
              Preencha o formulário abaixo para se juntar à nossa rede de barbeiros profissionais
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
                          <Input placeholder="Nome da sua Barbearia" className="pl-10" {...field} />
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
                          <Input placeholder="Nome Completo" className="pl-10" {...field} />
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
                          <Input placeholder="seu@email.com" className="pl-10" {...field} />
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
                          <Input placeholder="(55) 12345-6789" className="pl-10" {...field} />
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
                          <Input placeholder="Rua Principal, 123" className="pl-10" {...field} />
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
                          <Input placeholder="São Paulo" className="pl-10" {...field} />
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
                    <input id="dropzone-file" type="file" className="hidden" multiple />
                  </label>
                </div>
                <p className="text-xs text-barber-500 mt-2 text-center">
                  Envie fotos da sua barbearia e trabalhos anteriores para mostrar suas habilidades
                </p>
              </div>
              
              <FormField
                control={form.control}
                name="termsAndConditions"
                render={({ field }) => (
                  <FormItem className="flex items-start space-x-2 space-y-0">
                    <FormControl>
                      <Checkbox
                        checked={field.value}
                        onCheckedChange={field.onChange}
                      />
                    </FormControl>
                    <div className="space-y-1 leading-none">
                      <FormLabel className="text-sm">
                        Eu concordo com os{" "}
                        <a href="#" className="text-barber-900 hover:underline">
                          Termos de Serviço
                        </a>
                        {", "}
                        <a href="#" className="text-barber-900 hover:underline">
                          Política de Privacidade
                        </a>
                        {" e "}
                        <a href="#" className="text-barber-900 hover:underline">
                          Contrato do Barbeiro
                        </a>
                      </FormLabel>
                      <FormMessage />
                    </div>
                  </FormItem>
                )}
              />
              
              <div className="flex gap-4 pt-2">
                <Button 
                  type="button" 
                  variant="outline" 
                  className="flex-1"
                  onClick={() => navigate("/barbers")}
                >
                  Cancelar
                </Button>
                <Button 
                  type="submit" 
                  className="flex-1 bg-barber-900 hover:bg-barber-800"
                >
                  Enviar Inscrição
                </Button>
              </div>
            </form>
          </Form>
        </div>
      </div>
    </div>
  );
};

export default BarberSignup;
