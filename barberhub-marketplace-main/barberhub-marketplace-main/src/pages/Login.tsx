import { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import * as z from "zod";
import { Mail, Lock, Scissors, User } from "lucide-react";
import { useToast } from "@/hooks/use-toast";
import { useAuth } from "@/contexts/AuthContext";
import { Link } from "react-router-dom";

const loginSchema = z.object({
  email: z.string().email({ message: "Por favor, insira um e-mail válido" }),
  password: z.string().min(6, { message: "A senha deve ter pelo menos 6 caracteres" }),
});

type LoginFormValues = z.infer<typeof loginSchema>;

const Login = () => {
  const { toast } = useToast();
  const navigate = useNavigate();
  const location = useLocation();
  const { login } = useAuth();
  const [userType, setUserType] = useState<"client" | "barber">("client");

  const form = useForm<LoginFormValues>({
    resolver: zodResolver(loginSchema),
    defaultValues: {
      email: "",
      password: "",
    },
  });

  async function onSubmit(data: LoginFormValues) {
    try {
      await login(data.email, data.password);
      toast({
        title: "Login realizado com sucesso!",
        description: "Bem-vindo de volta!",
      });

      // Redireciona para a página que tentou acessar ou para a página inicial do tipo de usuário
      const from = location.state?.from?.pathname || (data.email === "cliente@email.com" ? "/client/profile" : "/barber/profile");
      navigate(from, { replace: true });
    } catch (error) {
      toast({
        title: "Erro ao fazer login",
        description: "E-mail ou senha incorretos",
        variant: "destructive",
      });
    }
  }

  return (
    <div className="min-h-screen bg-barber-50 flex items-center justify-center px-4">
      <div className="w-full max-w-md">
        <div className="text-center mb-8">
          <Link to="/" className="flex items-center justify-center gap-2 mb-4">
            <Scissors size={32} className="text-barber-900" />
            <span className="text-2xl font-semibold tracking-tight text-barber-900">BarberHub</span>
          </Link>
          <h1 className="text-3xl font-bold text-barber-900">Bem-vindo de volta!</h1>
          <p className="mt-2 text-barber-600">
            Faça login para acessar sua conta
          </p>
        </div>

        <div className="bg-white rounded-xl shadow-lg p-8">
          <div className="flex gap-4 mb-6">
            <Button
              variant={userType === "client" ? "default" : "outline"}
              className="flex-1"
              onClick={() => setUserType("client")}
            >
              <User size={16} className="mr-2" />
              Cliente
            </Button>
            <Button
              variant={userType === "barber" ? "default" : "outline"}
              className="flex-1"
              onClick={() => setUserType("barber")}
            >
              <Scissors size={16} className="mr-2" />
              Barbeiro
            </Button>
          </div>

          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
              <FormField
                control={form.control}
                name="email"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>E-mail</FormLabel>
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
                name="password"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Senha</FormLabel>
                    <div className="relative">
                      <Lock className="absolute left-3 top-2.5 h-5 w-5 text-muted-foreground" />
                      <FormControl>
                        <Input type="password" placeholder="Sua senha" className="pl-10" {...field} />
                      </FormControl>
                    </div>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <Button type="submit" className="w-full bg-barber-900 hover:bg-barber-800">
                Entrar
              </Button>
            </form>
          </Form>

          <div className="mt-6 text-center">
            <Link to="/reset-password" className="text-sm text-barber-600 hover:text-barber-900">
              Esqueceu sua senha?
            </Link>
          </div>
        </div>

        <div className="mt-8 text-center text-sm text-barber-600">
          <p>Para testar:</p>
          <p>Cliente: cliente@email.com / 123456</p>
          <p>Barbeiro: barbeiro@email.com / 123456</p>
        </div>
      </div>
    </div>
  );
};

export default Login;
