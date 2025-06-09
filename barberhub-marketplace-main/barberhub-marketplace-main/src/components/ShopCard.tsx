import { Star, MapPin, Clock } from "lucide-react";
import { cn } from "@/lib/utils";
import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { useAuth } from "@/contexts/AuthContext";
import { useToast } from "@/hooks/use-toast";
import { useNavigate } from "react-router-dom";

interface ShopCardProps {
  id: string;
  name: string;
  image: string;
  location: string;
  rating: number;
  reviews: number;
  services: string[];
  className?: string;
}

const ShopCard = ({
  id,
  name,
  image,
  location,
  rating,
  reviews,
  services,
  className
}: ShopCardProps) => {
  const { isAuthenticated, user } = useAuth();
  const { toast } = useToast();
  const navigate = useNavigate();

  const handleBookingClick = (e: React.MouseEvent) => {
    e.preventDefault();

    if (!isAuthenticated) {
      toast({
        title: "Login necessário",
        description: "Por favor, faça login para realizar um agendamento.",
        variant: "destructive",
      });
      navigate("/login", { state: { from: "/barbershops" } });
      return;
    }

    if (user?.type !== "client") {
      toast({
        title: "Acesso restrito",
        description: "Apenas clientes podem realizar agendamentos.",
        variant: "destructive",
      });
      return;
    }

    // Se passou pelas verificações, permite a navegação
    navigate(`/barbershops?shop=${id}`);
  };

  return (
    <div className={cn(
      "group relative bg-white rounded-lg shadow-sm overflow-hidden transition-all duration-300 hover:shadow-md",
      className
    )}>
      <div className="aspect-[4/3] overflow-hidden">
        <img
          src={image}
          alt={name}
          className="w-full h-full object-cover object-center transition-transform duration-500 group-hover:scale-105"
        />
      </div>
      
      <div className="p-5">
        <div className="flex items-start justify-between mb-2">
          <h3 className="text-lg font-semibold text-barber-900 line-clamp-1">{name}</h3>
          <div className="flex items-center">
            <Star size={16} className="text-yellow-500 fill-yellow-500 mr-1" />
            <span className="text-sm font-medium">{rating}</span>
            <span className="text-xs text-barber-500 ml-1">({reviews})</span>
          </div>
        </div>
        
        <div className="flex items-center mb-3 text-barber-500">
          <MapPin size={14} className="mr-1" />
          <span className="text-sm line-clamp-1">{location}</span>
        </div>
        
        <div className="flex flex-wrap gap-1 mb-4">
          {services.slice(0, 3).map((service, idx) => (
            <span key={idx} className="px-2 py-1 text-xs rounded-full bg-barber-100 text-barber-700">
              {service}
            </span>
          ))}
          {services.length > 3 && (
            <span className="px-2 py-1 text-xs rounded-full bg-barber-100 text-barber-700">
              +{services.length - 3} mais
            </span>
          )}
        </div>
        
        <div className="flex items-center justify-between">
          <div className="flex items-center text-barber-500 text-sm">
            <Clock size={14} className="mr-1" />
            <span>Disponível hoje</span>
          </div>
          <Button 
            size="sm"
            className="bg-barber-900 hover:bg-barber-800 text-white"
            onClick={handleBookingClick}
          >
            Agendar agora
          </Button>
        </div>
      </div>
    </div>
  );
};

export default ShopCard;
