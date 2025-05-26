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
import { Scissors, MapPin, User, Mail, Phone, Store, Clock, Image, Calendar, Upload, Building2, Hash, Home, Map } from "lucide-react";
import { useToast } from "@/hooks/use-toast";
import { AVAILABLE_SERVICES } from "@/constants/services";

const barberShopSchema = z.object({
  shopName: z.string().min(2, { message: "O nome da barbearia deve ter pelo menos 2 caracteres" }),
  ownerName: z.string().min(2, { message: "O nome do proprietário deve ter pelo menos 2 caracteres" }),
  cnpj: z.string().min(14, { message: "CNPJ inválido" }).max(18, { message: "CNPJ inválido" }),
  email: z.string().email({ message: "Por favor, insira um endereço de e-mail válido" }),
  phone: z.string().min(10, { message: "Por favor, insira um número de telefone válido" }),
  street: z.string().min(3, { message: "Rua inválida" }),
  number: z.string().min(1, { message: "Número inválido" }),
  neighborhood: z.string().min(2, { message: "Bairro inválido" }),
  city: z.string().min(2, { message: "Cidade inválida" }),
  zipCode: z.string().min(8, { message: "CEP inválido" }).max(9, { message: "CEP inválido" }),
  description: z.string().min(20, { message: "A descrição deve ter pelo menos 20 caracteres" }),
  services: z.array(z.string()).min(1, { message: "Selecione pelo menos um serviço" }),
  workingHours: z.string().min(5, { message: "Por favor, insira seus horários de funcionamento" }),
  termsAndConditions: z.literal(true, {
    errorMap: () => ({ message: "Você deve aceitar os termos e condições" }),
  }),
});

type BarberShopFormValues = z.infer<typeof barberShopSchema>;

const BarberSignup = () => {
  const { toast } = useToast();
  const navigate = useNavigate();
  const [termsRead, setTermsRead] = useState(false);
  const [privacyRead, setPrivacyRead] = useState(false);
  const [shopImage, setShopImage] = useState<string | null>(null);
  
  const form = useForm<BarberShopFormValues>({
    resolver: zodResolver(barberShopSchema),
    defaultValues: {
      shopName: "",
      ownerName: "",
      cnpj: "",
      email: "",
      phone: "",
      street: "",
      number: "",
      neighborhood: "",
      city: "",
      zipCode: "",
      description: "",
      services: [],
      workingHours: "",
      termsAndConditions: undefined,
    },
  });

  const handleImageUpload = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    if (file) {
      if (file.size > 5 * 1024 * 1024) { // 5MB
        toast({
          title: "Erro",
          description: "A imagem deve ter no máximo 5MB",
          variant: "destructive",
        });
        return;
      }

      const reader = new FileReader();
      reader.onloadend = () => {
        setShopImage(reader.result as string);
      };
      reader.readAsDataURL(file);
    }
  };

  function onSubmit(data: BarberShopFormValues) {
    if (!shopImage) {
      toast({
        title: "Erro",
        description: "Por favor, adicione uma foto da sua barbearia",
        variant: "destructive",
      });
      return;
    }

    console.log({ ...data, shopImage });
    toast({
      title: "Inscrição Enviada",
      description: "Obrigado por se inscrever! Analisaremos suas informações e entraremos em contato em breve.",
    });
    
    setTimeout(() => navigate("/barbers"), 2000);
  }

  const handleTermsClick = () => {
    setTermsRead(true);
    window.open("/termsofservice", "_blank");
  };

  const handlePrivacyClick = () => {
    setPrivacyRead(true);
    window.open("/privacypolicy", "_blank");
  };

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
              <div className="space-y-4">
                <h3 className="text-lg font-medium">Foto da Barbearia</h3>
                <div className="flex flex-col items-center gap-4">
                  <div className="relative w-full max-w-md aspect-[4/3] rounded-lg overflow-hidden border-2 border-barber-200">
                    <img
                      src={shopImage || "https://placehold.co/400x300/e2e8f0/64748b?text=Foto+da+Barbearia"}
                      alt="Foto da Barbearia"
                      className="w-full h-full object-cover"
                    />
                  </div>
                  <div className="w-full max-w-md">
                    <label className="flex flex-col items-center justify-center w-full h-32 border-2 border-barber-300 border-dashed rounded-lg cursor-pointer bg-barber-50 hover:bg-barber-100 transition-colors">
                      <div className="flex flex-col items-center justify-center pt-5 pb-6">
                        <Upload className="w-8 h-8 mb-1 text-barber-500" />
                        <p className="mb-2 text-sm text-barber-700">
                          <span className="font-semibold">Clique para adicionar uma foto</span> ou arraste e solte
                        </p>
                        <p className="text-xs text-barber-500">PNG, JPG ou WEBP (MÁX. 5MB)</p>
                      </div>
                      <input
                        type="file"
                        className="hidden"
                        accept="image/*"
                        onChange={handleImageUpload}
                      />
                    </label>
                  </div>
                </div>
              </div>

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
                  name="cnpj"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>CNPJ</FormLabel>
                      <div className="relative">
                        <Building2 className="absolute left-3 top-2.5 h-5 w-5 text-muted-foreground" />
                        <FormControl>
                          <Input placeholder="00.000.000/0000-00" className="pl-10" {...field} />
                        </FormControl>
                      </div>
                      <FormMessage />
                    </FormItem>
                  )}
                />

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
              </div>

              <div className="grid md:grid-cols-2 gap-6">
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

              <div className="space-y-4">
                <h3 className="text-lg font-medium">Endereço</h3>
                
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                  <FormField
                    control={form.control}
                    name="street"
                    render={({ field }) => (
                      <FormItem className="md:col-span-2">
                        <FormLabel>Rua</FormLabel>
                        <div className="relative">
                          <Home className="absolute left-3 top-2.5 h-5 w-5 text-muted-foreground" />
                          <FormControl>
                            <Input
                              placeholder="Nome da rua"
                              className="pl-10"
                              {...field}
                            />
                          </FormControl>
                        </div>
                        <FormMessage />
                      </FormItem>
                    )}
                  />

                  <FormField
                    control={form.control}
                    name="number"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Número</FormLabel>
                        <div className="relative">
                          <Hash className="absolute left-3 top-2.5 h-5 w-5 text-muted-foreground" />
                          <FormControl>
                            <Input
                              placeholder="Número"
                              className="pl-10"
                              {...field}
                            />
                          </FormControl>
                        </div>
                        <FormMessage />
                      </FormItem>
                    )}
                  />

                  <FormField
                    control={form.control}
                    name="neighborhood"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Bairro</FormLabel>
                        <div className="relative">
                          <MapPin className="absolute left-3 top-2.5 h-5 w-5 text-muted-foreground" />
                          <FormControl>
                            <Input
                              placeholder="Nome do bairro"
                              className="pl-10"
                              {...field}
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
                          <Building2 className="absolute left-3 top-2.5 h-5 w-5 text-muted-foreground" />
                          <FormControl>
                            <Input
                              placeholder="Nome da cidade"
                              className="pl-10"
                              {...field}
                            />
                          </FormControl>
                        </div>
                        <FormMessage />
                      </FormItem>
                    )}
                  />

                  <FormField
                    control={form.control}
                    name="zipCode"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>CEP</FormLabel>
                        <div className="relative">
                          <Map className="absolute left-3 top-2.5 h-5 w-5 text-muted-foreground" />
                          <FormControl>
                            <Input
                              placeholder="00000-000"
                              className="pl-10"
                              {...field}
                            />
                          </FormControl>
                        </div>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </div>
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
                  render={() => (
                    <FormItem>
                      <FormLabel>Serviços Oferecidos</FormLabel>
                      <div className="grid grid-cols-2 gap-4 mt-2">
                        {AVAILABLE_SERVICES.map((service) => (
                          <FormField
                            key={service.id}
                            control={form.control}
                            name="services"
                            render={({ field }) => {
                              return (
                                <FormItem
                                  key={service.id}
                                  className="flex flex-row items-start space-x-3 space-y-0"
                                >
                                  <FormControl>
                                    <Checkbox
                                      checked={field.value?.includes(service.id)}
                                      onCheckedChange={(checked) => {
                                        return checked
                                          ? field.onChange([...field.value, service.id])
                                          : field.onChange(
                                              field.value?.filter(
                                                (value) => value !== service.id
                                              )
                                            )
                                      }}
                                    />
                                  </FormControl>
                                  <FormLabel className="font-normal">
                                    {service.label}
                                  </FormLabel>
                                </FormItem>
                              )
                            }}
                          />
                        ))}
                      </div>
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

              <FormField
                control={form.control}
                name="termsAndConditions"
                render={({ field }) => (
                  <FormItem className="flex items-start space-x-2 space-y-0">
                    <FormControl>
                      <Checkbox
                        checked={field.value}
                        onCheckedChange={field.onChange}
                        disabled={!termsRead || !privacyRead}
                      />
                    </FormControl>
                    <div className="space-y-1 leading-none">
                      <FormLabel className="text-sm">
                        Eu concordo com os{" "}
                        <button
                          type="button"
                          onClick={handleTermsClick}
                          className="text-barber-900 hover:underline"
                        >
                          Termos de Serviço
                        </button>
                        {" e "}
                        <button
                          type="button"
                          onClick={handlePrivacyClick}
                          className="text-barber-900 hover:underline"
                        >
                          Política de Privacidade
                        </button>
                      </FormLabel>
                      {(!termsRead || !privacyRead) && (
                        <p className="text-sm text-amber-600">
                          Por favor, leia os termos de serviço e a política de privacidade antes de prosseguir
                        </p>
                      )}
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
                  disabled={!termsRead || !privacyRead}
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
