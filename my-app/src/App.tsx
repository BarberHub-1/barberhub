import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Index from "./pages/Index";
import Barbers from "./pages/Barbers";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import BarberSignup from "./pages/BarberSignup";
import About from "./pages/About";
import NotFound from "./pages/NotFound";
import Barbershops from './pages/Barbershops';

const queryClient = new QueryClient();

const App = () => (
  <QueryClientProvider client={queryClient}>
    <TooltipProvider>
      <Toaster />
      <Sonner />
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Index />} />
          <Route path="/barbers" element={<Barbers />} />
          <Route path="/barbershops" element={<Barbershops />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/barber-signup" element={<BarberSignup />} />
          <Route path="/about" element={<About />} />
          {/* ADICIONE TODAS AS ROTAS PERSONALIZADAS ACIMA DA ROTA CURINGA "*" */}
          <Route path="*" element={<NotFound />} />
        </Routes>
      </BrowserRouter>
    </TooltipProvider>
  </QueryClientProvider>
);

export default App;
