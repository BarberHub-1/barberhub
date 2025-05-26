import { useState } from "react";
import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Checkbox } from "@/components/ui/checkbox";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import * as z from "zod";
import { Mail, Lock, Eye, EyeOff } from "lucide-react";
import { useToast } from "@/hooks/use-toast";
import { Scissors } from "lucide-react";

const loginSchema = z.object({
  email: z.string().email({ message: "Por favor, insira um endereço de e-mail válido" }),
  password: z.string().min(6, { message: "A senha deve ter pelo menos 6 caracteres" }),
  rememberMe: z.boolean().default(false),
});

type LoginFormValues = z.infer<typeof loginSchema>;

const Login = () => {
  const [showPassword, setShowPassword] = useState(false);
  const { toast } = useToast();
  
  const form = useForm<LoginFormValues>({
    resolver: zodResolver(loginSchema),
    defaultValues: {
      email: "",
      password: "",
      rememberMe: false,
    },
  });

  function onSubmit(data: LoginFormValues) {
    // Em uma aplicação real, isso chamaria uma API de autenticação
    console.log(data);
    toast({
      title: "Tentativa de Login",
      description: `Logado com ${data.email}`,
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
          <h1 className="text-2xl font-bold text-gray-900">Bem-vindo de volta</h1>
          <p className="mt-2 text-sm text-gray-600">
            Faça login na sua conta para continuar
          </p>
        </div>

        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
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
            
            <div className="flex items-center justify-between">
              <FormField
                control={form.control}
                name="rememberMe"
                render={({ field }) => (
                  <FormItem className="flex items-center space-x-2 space-y-0">
                    <FormControl>
                      <Checkbox
                        checked={field.value}
                        onCheckedChange={field.onChange}
                      />
                    </FormControl>
                    <FormLabel className="text-sm cursor-pointer">Lembrar de mim</FormLabel>
                  </FormItem>
                )}
              />
              <a href="#" className="text-sm font-medium text-barber-900 hover:text-barber-800">
                Esqueceu a senha?
              </a>
            </div>
            
            <Button type="submit" className="w-full bg-barber-900 hover:bg-barber-800">
              Entrar
            </Button>
            
            <div className="text-center mt-4">
              <p className="text-sm text-gray-600">
                Não tem uma conta?{" "}
                <Link to="/signup" className="font-medium text-barber-900 hover:text-barber-800">
                  Cadastre-se
                </Link>
              </p>
            </div>
          </form>
        </Form>
      </div>
    </div>
  );
};

export default Login;
