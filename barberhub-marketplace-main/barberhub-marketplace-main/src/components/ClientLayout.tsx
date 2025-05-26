import { Link, Outlet, useLocation, useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Calendar, User, LogOut, History } from "lucide-react";
import { Scissors } from "lucide-react";
import { useAuth } from "@/contexts/AuthContext";

const ClientLayout = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { logout } = useAuth();

  const isActive = (path: string) => {
    return location.pathname === path;
  };

  const handleLogout = () => {
    logout();
    navigate("/");
  };

  return (
    <div className="min-h-screen bg-barber-50">
      {/* Barra de navegação superior */}
      <nav className="fixed top-0 left-0 right-0 h-16 bg-white border-b z-50">
        <div className="container mx-auto px-4 h-full flex items-center justify-between">
          <Link to="/client/profile" className="flex items-center gap-2">
            <Scissors size={24} className="text-barber-900" />
            <span className="text-lg font-semibold tracking-tight text-barber-900">BarberHub</span>
          </Link>
          <Button variant="ghost" onClick={handleLogout}>
            <LogOut className="w-4 h-4 mr-2" />
            Sair
          </Button>
        </div>
      </nav>

      {/* Sidebar */}
      <div className="fixed left-0 top-16 w-64 h-[calc(100vh-4rem)] bg-white border-r">
        <div className="p-4">
          <nav className="space-y-2">
            <Link to="/client/appointments">
              <Button
                variant={isActive("/client/appointments") ? "default" : "ghost"}
                className="w-full justify-start"
              >
                <Calendar className="w-4 h-4 mr-2" />
                Agendamentos
              </Button>
            </Link>
            <Link to="/client/history">
              <Button
                variant={isActive("/client/history") ? "default" : "ghost"}
                className="w-full justify-start"
              >
                <History className="w-4 h-4 mr-2" />
                Histórico
              </Button>
            </Link>
            <Link to="/client/profile">
              <Button
                variant={isActive("/client/profile") ? "default" : "ghost"}
                className="w-full justify-start"
              >
                <User className="w-4 h-4 mr-2" />
                Perfil
              </Button>
            </Link>
          </nav>
        </div>
      </div>

      {/* Conteúdo principal */}
      <main className="ml-64 pt-16">
        <Outlet />
      </main>
    </div>
  );
};

export default ClientLayout; 