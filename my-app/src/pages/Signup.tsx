import { useState } from "react";
import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Checkbox } from "@/components/ui/checkbox";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import * as z from "zod";
import { Mail, Lock, User, Eye, EyeOff } from "lucide-react";
import { useToast } from "@/hooks/use-toast";
import { Scissors } from "lucide-react";

const signupSchema = z.object({
  name: z.string().min(2, { message: "O nome deve ter pelo menos 2 caracteres" }),
  email: z.string().email({ message: "Por favor, insira um endereço de e-mail válido" }),
  password: z.string().min(6, { message: "A senha deve ter pelo menos 6 caracteres" }),
  termsAndConditions: z.literal(true, {
    errorMap: () => ({ message: "Você deve aceitar os termos e condições" }),
  }),
});

type SignupFormValues = z.infer<typeof signupSchema>;

const Signup = () => {
  const [showPassword, setShowPassword] = useState(false);
  const { toast } = useToast();
  
  const form = useForm<SignupFormValues>({
    resolver: zodResolver(signupSchema),
    defaultValues: {
      name: "",
      email: "",
      password: "",
      termsAndConditions: undefined,
    },
  });

  function onSubmit(data: SignupFormValues) {
    // Em uma aplicação real, isso chamaria uma API de registro
    console.log(data);
    toast({
      title: "Conta Criada",
      description: `Bem-vindo, ${data.name}! Sua conta foi criada.`,
    });
  }

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-slate-50">
      <div className="w-full max-w-md p-8 space-y-8 bg-white rounded-xl shadow-lg">
        <div className="text-center">
          <Link to="/" className="inline-flex items-center gap-2 mb-8">
            <Scissors size={28} className="text-barber-900" />
            <span className="text-xl font-semibold tracking-tight text-barber-900">BarberHub</span>
          </Link>
          <h1 className="text-2xl font-bold text-gray-900">Criar uma conta</h1>
          <p className="mt-2 text-sm text-gray-600">
            Junte-se ao BarberHub e descubra os melhores barbeiros da sua região
          </p>
        </div>

        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
            <FormField
              control={form.control}
              name="name"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Nome Completo</FormLabel>
                  <div className="relative">
                    <User className="absolute left-3 top-2.5 h-5 w-5 text-muted-foreground" />
                    <FormControl>
                      <Input
                        placeholder="João Silva"
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
              name="email"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>E-mail</FormLabel>
                  <div className="relative">
                    <Mail className="absolute left-3 top-2.5 h-5 w-5 text-muted-foreground" />
                    <FormControl>
                      <Input
                        placeholder="seu@email.com"
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
              name="password"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Senha</FormLabel>
                  <div className="relative">
                    <Lock className="absolute left-3 top-2.5 h-5 w-5 text-muted-foreground" />
                    <FormControl>
                      <Input
                        type={showPassword ? "text" : "password"}
                        className="pl-10 pr-10"
                        {...field}
                      />
                    </FormControl>
                    <button
                      type="button"
                      className="absolute right-3 top-2.5 text-muted-foreground"
                      onClick={() => setShowPassword(!showPassword)}
                    >
                      {showPassword ? (
                        <EyeOff className="h-5 w-5" />
                      ) : (
                        <Eye className="h-5 w-5" />
                      )}
                    </button>
                  </div>
                  <FormMessage />
                </FormItem>
              )}
            />
            
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
                      </a>{" "}
                      e{" "}
                      <a href="#" className="text-barber-900 hover:underline">
                        Política de Privacidade
                      </a>
                    </FormLabel>
                    <FormMessage />
                  </div>
                </FormItem>
              )}
            />
            
            <Button type="submit" className="w-full bg-barber-900 hover:bg-barber-800">
              Criar conta
            </Button>
            
            <div className="text-center mt-4">
              <p className="text-sm text-gray-600">
                Já tem uma conta?{" "}
                <Link to="/login" className="font-medium text-barber-900 hover:text-barber-800">
                  Faça login
                </Link>
              </p>
            </div>
          </form>
        </Form>
      </div>
    </div>
  );
};

export default Signup;
