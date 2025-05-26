import { Navigate, useLocation } from "react-router-dom";
import { useAuth } from "@/contexts/AuthContext";

type PrivateRouteProps = {
  children: React.ReactNode;
  requiredUserType?: "client" | "barber";
};

export function PrivateRoute({ children, requiredUserType }: PrivateRouteProps) {
  const { isAuthenticated, user } = useAuth();
  const location = useLocation();

  if (!isAuthenticated) {
    // Redireciona para o login e salva a rota que tentou acessar
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  if (requiredUserType && user?.type !== requiredUserType) {
    // Redireciona para a página inicial se o tipo de usuário não corresponder
    return <Navigate to="/" replace />;
  }

  return <>{children}</>;
} 