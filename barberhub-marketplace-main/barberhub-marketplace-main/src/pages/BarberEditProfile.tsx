import { useState, useEffect } from "react";
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
import { useAuth } from "@/contexts/AuthContext";
import { useQuery, useMutation } from "@tanstack/react-query";
import api from "@/lib/axios";

interface HorarioFuncionamentoDTO {
  id: number;
  diaSemana: string;
  horarioAbertura: string;
  horarioFechamento: string;
}

interface EstabelecimentoDTO {
  id: number;
  nomeProprietario: string;
  nomeEstabelecimento: string;
  cnpj: string;
  endereco: string;
  cidade: string;
  cep: string;
  telefone: string;
  email: string;
  foto?: string;
  status: string;
  horario: HorarioFuncionamentoDTO[];
  descricao?: string;
  servicos?: string[];
}

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
  horarios: z.array(z.object({
    diaSemana: z.string(),
    horarioAbertura: z.string().regex(/^([01]?\d|2[0-3]):[0-5]\d$/, "Formato inválido (HH:mm)"),
    horarioFechamento: z.string().regex(/^([01]?\d|2[0-3]):[0-5]\d$/, "Formato inválido (HH:mm)")
  }))
});

type BarberShopFormValues = z.infer<typeof barberShopSchema>;

const BarberEditProfile = () => {
  const { toast } = useToast();
  const navigate = useNavigate();
  const { user } = useAuth();
  const [shopImage, setShopImage] = useState<string | null>("https://placehold.co/400x300/e2e8f0/64748b?text=Foto+da+Barbearia");
  const [imageFile, setImageFile] = useState<File | null>(null);
  
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
      horarios: [],
    },
  });

  const { data: estabelecimento, isLoading } = useQuery<EstabelecimentoDTO | undefined>({
    queryKey: ['estabelecimento', user?.id],
    queryFn: async () => {
      if (!user?.id) return undefined;
      const response = await api.get<EstabelecimentoDTO>(`/api/estabelecimentos/${user.id}`);
      console.log('Dados do estabelecimento:', response.data);
      return response.data;
    },
    enabled: !!user?.id,
  });

  const updateMutation = useMutation({
    mutationFn: async (data: BarberShopFormValues) => {
      const formData = new FormData();
      
      // Adiciona os dados do estabelecimento como JSON
      const estabelecimentoData = {
        nomeEstabelecimento: data.shopName,
        nomeProprietario: data.ownerName,
        cnpj: data.cnpj,
        email: data.email,
        telefone: data.phone,
        endereco: `${data.street}, ${data.number} - ${data.neighborhood}`,
        cidade: data.city,
        cep: data.zipCode,
        descricao: data.description,
        servicos: data.services,
        horario: data.horarios
      };
      
      formData.append('estabelecimento', new Blob([JSON.stringify(estabelecimentoData)], {
        type: 'application/json'
      }));
      
      // Adiciona a foto se existir
      if (imageFile) {
        formData.append('foto', imageFile);
      }

      const response = await api.put(`/api/estabelecimentos/${user?.id}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      return response.data;
    },
    onSuccess: () => {
      toast({
        title: "Perfil Atualizado",
        description: "Suas informações foram atualizadas com sucesso!",
      });
      navigate("/barber/profile");
    },
    onError: (error) => {
      console.error('Erro ao atualizar:', error);
      toast({
        title: "Erro",
        description: "Não foi possível atualizar o perfil. Tente novamente.",
        variant: "destructive",
      });
    },
  });

  useEffect(() => {
    if (estabelecimento) {
      console.log('Foto do estabelecimento:', estabelecimento.foto);
      
      let street = "";
      let number = "";
      let neighborhood = "";
      let city = estabelecimento.cidade || "";
      let zipCode = estabelecimento.cep || "";

      if (estabelecimento.endereco) {
        const parts = estabelecimento.endereco.split(',');
        street = parts[0]?.trim() || "";
        const rest = parts[1]?.trim();

        if (rest) {
          const restParts = rest.split('-');
          number = restParts[0]?.trim() || "";
          neighborhood = restParts[1]?.trim() || "";
        }
      }

      let workingHoursText = "";
      if (estabelecimento.horario && estabelecimento.horario.length > 0) {
          workingHoursText = estabelecimento.horario.map(h => `${h.diaSemana}: ${h.horarioAbertura}-${h.horarioFechamento}`).join('\n');
      }

      form.reset({
        shopName: estabelecimento.nomeEstabelecimento || "",
        ownerName: estabelecimento.nomeProprietario || "",
        cnpj: estabelecimento.cnpj || "",
        email: estabelecimento.email || "",
        phone: estabelecimento.telefone || "",
        street: street,
        number: number,
        neighborhood: neighborhood,
        city: city,
        zipCode: zipCode,
        description: estabelecimento.descricao || "",
        services: estabelecimento.servicos || [],
        horarios: estabelecimento.horario || []
      });

      // Atualizar a foto se existir
      if (estabelecimento.foto) {
        console.log('Atualizando foto para:', estabelecimento.foto);
        // Verifica se a foto já está no formato data:image
        if (estabelecimento.foto.startsWith('data:image')) {
          setShopImage(estabelecimento.foto);
        } else {
          // Se não estiver, adiciona o prefixo data:image/jpeg;base64,
          setShopImage(`data:image/jpeg;base64,${estabelecimento.foto}`);
        }
      } else {
        console.log('Nenhuma foto encontrada, usando placeholder');
        setShopImage("https://placehold.co/400x300/e2e8f0/64748b?text=Foto+da+Barbearia");
      }
    }
  }, [estabelecimento, form]);

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

      setImageFile(file);
      const reader = new FileReader();
      reader.onloadend = () => {
        const base64String = reader.result as string;
        setShopImage(base64String);
      };
      reader.readAsDataURL(file);
    }
  };

  function onSubmit(data: BarberShopFormValues) {
    // Formatar CNPJ e Telefone para o formato esperado pelo backend
    const formattedData = {
      ...data,
      cnpj: data.cnpj.replace(/\D/g, '').replace(/^(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})$/, '$1.$2.$3/$4-$5'),
      phone: data.phone.replace(/\D/g, '').replace(/^(\d{2})(\d{5})(\d{4})$/, '($1) $2-$3'),
    };
    updateMutation.mutate(formattedData);
  }

  const diasSemana = ['SEGUNDA', 'TERCA', 'QUARTA', 'QUINTA', 'SEXTA', 'SABADO', 'DOMINGO'];

  if (isLoading) {
    return (
      <div className="min-h-screen bg-barber-50 pt-24 pb-12">
        <div className="container mx-auto max-w-3xl px-4">
          <div className="bg-white rounded-xl shadow-lg p-8">
            <div className="text-center">
              <p>Carregando...</p>
            </div>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-barber-50 pt-24 pb-12">
      <div className="container mx-auto max-w-3xl px-4">
        <div className="bg-white rounded-xl shadow-lg p-8">
          <div className="text-center mb-8">
            <div className="flex items-center justify-center gap-2 mb-6">
              <Scissors size={28} className="text-barber-900" />
              <span className="text-xl font-semibold tracking-tight text-barber-900">BarberHub</span>
            </div>
            <h1 className="text-3xl font-bold text-barber-900">Editar Perfil da Barbearia</h1>
            <p className="mt-2 text-barber-600">
              Atualize as informações do seu perfil para manter seus clientes informados
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
                      onError={(e) => {
                        console.error('Erro ao carregar imagem:', e);
                        e.currentTarget.src = "https://placehold.co/400x300/e2e8f0/64748b?text=Foto+da+Barbearia";
                      }}
                    />
                  </div>
                  <div className="w-full max-w-md">
                    <label className="flex flex-col items-center justify-center w-full h-32 border-2 border-barber-300 border-dashed rounded-lg cursor-pointer bg-barber-50 hover:bg-barber-100 transition-colors">
                      <div className="flex flex-col items-center justify-center pt-5 pb-6">
                        <Upload className="w-8 h-8 mb-1 text-barber-500" />
                        <p className="mb-2 text-sm text-barber-700">
                          <span className="font-semibold">Clique para alterar a foto</span> ou arraste e solte
                        </p>
                        <p className="text-xs text-barber-500">PNG, JPG ou WEBP (MÁX. 5MB cada)</p>
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
                        <FormControl>
                          <Input
                            placeholder="123"
                            {...field}
                          />
                        </FormControl>
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
                        <FormControl>
                          <Input
                            placeholder="Centro"
                            {...field}
                          />
                        </FormControl>
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
                        <FormControl>
                          <Input
                            placeholder="Sua cidade"
                            {...field}
                          />
                        </FormControl>
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
                        <FormControl>
                          <Input
                            placeholder="00000-000"
                            {...field}
                          />
                        </FormControl>
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
                  name="horarios"
                  render={() => (
                    <FormItem>
                      <FormLabel>Horários de Funcionamento</FormLabel>
                      <div className="space-y-4">
                        {diasSemana.map((dia, index) => (
                          <div key={dia} className="grid grid-cols-3 gap-4 items-center">
                            <div className="font-medium">{dia}</div>
                            <FormField
                              control={form.control}
                              name={`horarios.${index}.horarioAbertura`}
                              render={({ field }) => (
                                <FormItem>
                                  <FormControl>
                                    <Input
                                      type="time"
                                      {...field}
                                      onChange={(e) => {
                                        field.onChange(e.target.value);
                                        form.setValue(`horarios.${index}.diaSemana`, dia);
                                      }}
                                    />
                                  </FormControl>
                                  <FormMessage />
                                </FormItem>
                              )}
                            />
                            <FormField
                              control={form.control}
                              name={`horarios.${index}.horarioFechamento`}
                              render={({ field }) => (
                                <FormItem>
                                  <FormControl>
                                    <Input
                                      type="time"
                                      {...field}
                                    />
                                  </FormControl>
                                  <FormMessage />
                                </FormItem>
                              )}
                            />
                          </div>
                        ))}
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
              
              <div className="flex justify-end gap-4">
                <Button
                  type="button"
                  variant="outline"
                  onClick={() => navigate("/barber/profile")}
                >
                  Cancelar
                </Button>
                <Button type="submit" disabled={updateMutation.isPending}>
                  {updateMutation.isPending ? "Salvando..." : "Salvar Alterações"}
                </Button>
              </div>
            </form>
          </Form>
        </div>
      </div>
    </div>
  );
};

export default BarberEditProfile; 