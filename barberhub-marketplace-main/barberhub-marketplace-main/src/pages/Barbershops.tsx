import { useState, useEffect } from 'react';
import { Star, MapPin, Clock, Scissors, X } from 'lucide-react';
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Link, useSearchParams, useNavigate } from "react-router-dom";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Calendar } from "@/components/ui/calendar";
import { format } from "date-fns";
import { ptBR } from "date-fns/locale";
import SearchFilters from "@/components/SearchFilters";
import { ServiceId, AVAILABLE_SERVICES, getServiceLabel } from "@/constants/services";
import { Checkbox } from "@/components/ui/checkbox";
import { Label } from "@/components/ui/label";
import { useAuth } from "@/contexts/AuthContext";
import { useToast } from "@/hooks/use-toast";

interface BarberShop {
  id: string;
  name: string;
  rating: number;
  reviews: number;
  location: string;
  services: ServiceId[];
  priceRange: [number, number];
  image: string;
  workingHours: {
    monday: { start: string; end: string } | null;
    tuesday: { start: string; end: string } | null;
    wednesday: { start: string; end: string } | null;
    thursday: { start: string; end: string } | null;
    friday: { start: string; end: string } | null;
    saturday: { start: string; end: string } | null;
    sunday: { start: string; end: string } | null;
  };
  availableTimes: string[];
}

const Barbershops = () => {
  const { isAuthenticated, user } = useAuth();
  const { toast } = useToast();
  const navigate = useNavigate();
  const [searchParams, setSearchParams] = useSearchParams();
  const [selectedShop, setSelectedShop] = useState<BarberShop | null>(null);
  const [selectedDate, setSelectedDate] = useState<Date | null>(null);
  const [selectedTime, setSelectedTime] = useState<string | null>(null);
  const [selectedServices, setSelectedServices] = useState<ServiceId[]>([]);
  const [filters, setFilters] = useState({
    services: [] as ServiceId[],
    location: searchParams.get("location") || "",
    rating: 0,
    priceRange: [0, 200] as [number, number],
    searchTerm: searchParams.get("search") || "",
  });

  const barbershops: BarberShop[] = [
    {
      id: "1",
      name: "The Classic Cut",
      rating: 4.8,
      reviews: 124,
      location: "Centro, São Paulo",
      services: ["corte-cabelo", "barba", "sobrancelha"],
      priceRange: [50, 100],
      image: "https://images.unsplash.com/photo-1562322140-8baeececf3df?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2069&q=80",
      workingHours: {
        monday: { start: "09:00", end: "19:00" },
        tuesday: { start: "09:00", end: "19:00" },
        wednesday: { start: "09:00", end: "19:00" },
        thursday: { start: "09:00", end: "19:00" },
        friday: { start: "09:00", end: "19:00" },
        saturday: { start: "10:00", end: "17:00" },
        sunday: null
      },
      availableTimes: [
        "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"
      ]
    },
    {
      id: "2",
      name: "Modern Barber",
      rating: 4.9,
      reviews: 98,
      location: "Jardins, São Paulo",
      services: ["corte-cabelo", "barba", "luzes"],
      priceRange: [60, 120],
      image: "https://img.freepik.com/fotos-gratis/cadeiras-na-barbearia-masculina-verde-em-estilo-retro_627829-8284.jpg?t=st=1745341620~exp=1745345220~hmac=52dc1a3fdbcbfebf95474eb3df0d1b43ce1c7bcbecec6aed323691375577eb0b&w=996",
      workingHours: {
        monday: { start: "09:00", end: "19:00" },
        tuesday: { start: "09:00", end: "19:00" },
        wednesday: { start: "09:00", end: "19:00" },
        thursday: { start: "09:00", end: "19:00" },
        friday: { start: "09:00", end: "19:00" },
        saturday: { start: "10:00", end: "17:00" },
        sunday: null
      },
      availableTimes: [
        "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"
      ]
    },
    {
      id: "3",
      name: "Elite Barbershop",
      rating: 4.7,
      reviews: 156,
      location: "Vila Madalena, São Paulo",
      services: ["corte-cabelo", "barba", "descoloração"],
      priceRange: [70, 150],
      image: "https://images.unsplash.com/photo-1534297635766-a262cdcb8ee4?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80",
      workingHours: {
        monday: { start: "09:00", end: "19:00" },
        tuesday: { start: "09:00", end: "19:00" },
        wednesday: { start: "09:00", end: "19:00" },
        thursday: { start: "09:00", end: "19:00" },
        friday: { start: "09:00", end: "19:00" },
        saturday: { start: "10:00", end: "17:00" },
        sunday: null
      },
      availableTimes: [
        "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"
      ]
    },
    {
      id: "4",
      name: "Premium Cuts",
      rating: 4.9,
      reviews: 87,
      location: "Moema, São Paulo",
      services: ["corte-cabelo", "barba", "progressiva"],
      priceRange: [80, 160],
      image: "https://images.unsplash.com/photo-1562322140-8baeececf3df?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2069&q=80",
      workingHours: {
        monday: { start: "09:00", end: "19:00" },
        tuesday: { start: "09:00", end: "19:00" },
        wednesday: { start: "09:00", end: "19:00" },
        thursday: { start: "09:00", end: "19:00" },
        friday: { start: "09:00", end: "19:00" },
        saturday: { start: "10:00", end: "17:00" },
        sunday: null
      },
      availableTimes: [
        "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"
      ]
    }
  ];

  useEffect(() => {
    const shopId = searchParams.get('shop');
    if (shopId) {
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
        navigate("/");
        return;
      }

      const shop = barbershops.find(s => s.id === shopId);
      if (shop) {
        setSelectedShop(shop);
      }
    }
  }, [searchParams, isAuthenticated, user]);

  useEffect(() => {
    const location = searchParams.get("location");
    const search = searchParams.get("search");
    
    setFilters(prev => ({
      ...prev,
      location: location || "",
      searchTerm: search || "",
    }));
  }, [searchParams]);

  const filteredBarbershops = barbershops.filter(shop => {
    // Filtro por termo de busca
    if (filters.searchTerm && !shop.name.toLowerCase().includes(filters.searchTerm.toLowerCase())) {
      return false;
    }

    // Filtro por serviços
    if (filters.services.length > 0) {
      const hasAllServices = filters.services.every((service) =>
        shop.services.includes(service)
      );
      if (!hasAllServices) return false;
    }

    // Filtro por localização
    if (filters.location && !shop.location.toLowerCase().includes(filters.location.toLowerCase())) {
      return false;
    }

    // Filtro por avaliação
    if (filters.rating > 0 && shop.rating < filters.rating) {
      return false;
    }

    // Filtro por preço
    if (
      shop.priceRange[0] < filters.priceRange[0] ||
      shop.priceRange[1] > filters.priceRange[1]
    ) {
      return false;
    }

    return true;
  });

  const handleServiceChange = (service: ServiceId) => {
    setSelectedServices(prev => {
      if (prev.includes(service)) {
        return prev.filter(s => s !== service);
      } else {
        return [...prev, service];
      }
    });
  };

  const handleBooking = () => {
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

    if (selectedDate && selectedTime && selectedServices.length > 0) {
      const servicesList = selectedServices.map(service => getServiceLabel(service)).join(", ");
      toast({
        title: "Agendamento realizado!",
        description: `Barbearia: ${selectedShop?.name}\nServiços: ${servicesList}\nData: ${format(selectedDate, "dd/MM/yyyy")}\nHorário: ${selectedTime}`,
      });
      setSelectedShop(null);
      setSelectedDate(null);
      setSelectedTime(null);
      setSelectedServices([]);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50">
      <nav className="fixed top-0 left-0 right-0 z-50 bg-white shadow-sm py-4">
        <div className="container mx-auto px-6">
          <Link to="/" className="flex items-center gap-2">
            <Scissors size={28} className="text-barber-900" />
            <span className="text-xl font-semibold tracking-tight text-barber-900">BarberHub</span>
          </Link>
        </div>
      </nav>

      <div className="container mx-auto px-4 py-8 mt-20">
        <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4 mb-8">
          <div>
            <h1 className="text-3xl font-bold text-barber-900 mb-2">Barbearias</h1>
            <p className="text-gray-600">Encontre a barbearia perfeita para você</p>
          </div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
          <div className="md:col-span-1">
            <SearchFilters onFiltersChange={setFilters} />
          </div>

          <div className="md:col-span-3">
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
              {filteredBarbershops.map((shop) => (
                <div key={shop.id} className="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow">
                  <div className="relative h-48">
                    <img
                      src={shop.image}
                      alt={shop.name}
                      className="w-full h-full object-cover"
                    />
                    <div className="absolute top-4 right-4 bg-white/90 px-3 py-1 rounded-full flex items-center gap-1">
                      <Star className="w-4 h-4 text-yellow-500 fill-yellow-500" />
                      <span className="text-sm font-medium">{shop.rating}</span>
                    </div>
                  </div>
                  
                  <div className="p-4">
                    <h3 className="text-xl font-semibold text-barber-900 mb-2">{shop.name}</h3>
                    
                    <div className="flex items-center gap-2 text-gray-600 mb-2">
                      <MapPin className="w-4 h-4" />
                      <span className="text-sm">{shop.location}</span>
                    </div>
                    
                    <div className="flex items-center gap-2 text-gray-600 mb-4">
                      <Clock className="w-4 h-4" />
                      <span className="text-sm">R$ {shop.priceRange[0]} - R$ {shop.priceRange[1]}</span>
                    </div>
                    
                    <div className="flex flex-wrap gap-2 mb-4">
                      {shop.services.map((service, index) => (
                        <span
                          key={index}
                          className="bg-barber-100 text-barber-800 text-xs px-2 py-1 rounded-full"
                        >
                          {getServiceLabel(service)}
                        </span>
                      ))}
                    </div>
                    
                    <div className="flex justify-between items-center">
                      <span className="text-sm text-gray-500">{shop.reviews} avaliações</span>
                      <Button 
                        className="bg-barber-900 hover:bg-barber-800"
                        onClick={() => setSelectedShop(shop)}
                      >
                        Ver Detalhes
                      </Button>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>

      <Dialog open={selectedShop !== null} onOpenChange={() => setSelectedShop(null)}>
        <DialogContent className="max-w-3xl">
          <DialogHeader>
            <DialogTitle className="text-2xl font-bold text-barber-900">
              {selectedShop?.name}
            </DialogTitle>
          </DialogHeader>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-4">
            <div>
              <img
                src={selectedShop?.image}
                alt={selectedShop?.name}
                className="w-full h-64 object-cover rounded-lg"
              />
              
              <div className="mt-4">
                <h3 className="font-semibold text-lg mb-2">Informações</h3>
                <div className="space-y-2">
                  <div className="flex items-center gap-2">
                    <MapPin className="w-4 h-4 text-gray-600" />
                    <span>{selectedShop?.location}</span>
                  </div>
                  <div className="flex items-center gap-2">
                    <Clock className="w-4 h-4 text-gray-600" />
                    <span>R$ {selectedShop?.priceRange[0]} - R$ {selectedShop?.priceRange[1]}</span>
                  </div>
                </div>
              </div>

              <div className="mt-4">
                <h3 className="font-semibold text-lg mb-2">Horário de Funcionamento</h3>
                <div className="space-y-1 text-sm">
                  <p>Segunda a Sexta: {selectedShop?.workingHours?.monday?.start} - {selectedShop?.workingHours?.monday?.end}</p>
                  <p>Sábado: {selectedShop?.workingHours?.saturday?.start} - {selectedShop?.workingHours?.saturday?.end}</p>
                  <p>Domingo: Fechado</p>
                </div>
              </div>
            </div>

            <div className="space-y-6">
              <div>
                <h3 className="font-semibold text-lg mb-2">Selecione os Serviços</h3>
                <div className="space-y-2">
                  {selectedShop?.services.map((service) => (
                    <div key={service} className="flex items-center space-x-2">
                      <Checkbox
                        id={service}
                        checked={selectedServices.includes(service)}
                        onCheckedChange={() => handleServiceChange(service)}
                      />
                      <Label htmlFor={service}>{getServiceLabel(service)}</Label>
                    </div>
                  ))}
                </div>
              </div>

              <div>
                <h3 className="font-semibold text-lg mb-2">Selecione a Data</h3>
                <Calendar
                  mode="single"
                  selected={selectedDate}
                  onSelect={setSelectedDate}
                  locale={ptBR}
                  disabled={(date) => {
                    const day = date.getDay();
                    return day === 0 || (day === 6 && !selectedShop?.workingHours?.saturday);
                  }}
                  className="rounded-md border"
                />
              </div>

              {selectedDate && (
                <div>
                  <h3 className="font-semibold text-lg mb-2">Selecione o Horário</h3>
                  <div className="grid grid-cols-3 gap-2">
                    {selectedShop?.availableTimes.map((time) => (
                      <Button
                        key={time}
                        variant={selectedTime === time ? "default" : "outline"}
                        className="w-full"
                        onClick={() => setSelectedTime(time)}
                      >
                        {time}
                      </Button>
                    ))}
                  </div>
                </div>
              )}

              <Button
                className="w-full bg-barber-900 hover:bg-barber-800"
                onClick={handleBooking}
                disabled={!selectedDate || !selectedTime || selectedServices.length === 0}
              >
                Agendar Horário
              </Button>
            </div>
          </div>
        </DialogContent>
      </Dialog>
    </div>
  );
};

export default Barbershops; 