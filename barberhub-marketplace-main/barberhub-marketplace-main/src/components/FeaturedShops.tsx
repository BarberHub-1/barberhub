import { Star, MapPin, Clock, Scissors } from 'lucide-react';
import { Link } from 'react-router-dom';
import { ServiceId, getServiceLabel } from '@/constants/services';

interface FeaturedShop {
  id: string;
  name: string;
  rating: number;
  reviews: number;
  location: string;
  services: ServiceId[];
  priceRange: [number, number];
  image: string;
}

const featuredShops: FeaturedShop[] = [
  {
    id: "1",
    name: "The Classic Cut",
    rating: 4.8,
    reviews: 124,
    location: "Centro, São Paulo",
    services: ["corte-cabelo", "barba", "sobrancelha"],
    priceRange: [50, 100],
    image: "https://images.unsplash.com/photo-1562322140-8baeececf3df?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2069&q=80"
  },
  {
    id: "2",
    name: "Modern Barber",
    rating: 4.9,
    reviews: 98,
    location: "Jardins, São Paulo",
    services: ["corte-cabelo", "barba", "luzes"],
    priceRange: [60, 120],
    image: "https://img.freepik.com/fotos-gratis/cadeiras-na-barbearia-masculina-verde-em-estilo-retro_627829-8284.jpg?t=st=1745341620~exp=1745345220~hmac=52dc1a3fdbcbfebf95474eb3df0d1b43ce1c7bcbecec6aed323691375577eb0b&w=996"
  },
  {
    id: "3",
    name: "Elite Barbershop",
    rating: 4.7,
    reviews: 156,
    location: "Vila Madalena, São Paulo",
    services: ["corte-cabelo", "barba", "descoloração"],
    priceRange: [70, 150],
    image: "https://images.unsplash.com/photo-1534297635766-a262cdcb8ee4?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80"
  }
];

const FeaturedShops = () => {
  return (
    <section className="py-16 bg-gray-50">
      <div className="container mx-auto px-4">
        <div className="text-center mb-12">
          <h2 className="text-3xl font-bold text-barber-900 mb-4">Barbearias em Destaque</h2>
          <p className="text-gray-600 max-w-2xl mx-auto">
            Conheça algumas das melhores barbearias parceiras do BarberHub, 
            selecionadas com base na qualidade dos serviços e satisfação dos clientes.
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          {featuredShops.map((shop) => (
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
              
              <div className="p-6">
                <h3 className="text-xl font-semibold text-barber-900 mb-2">{shop.name}</h3>
                
                <div className="flex items-center gap-2 text-gray-600 mb-2">
                  <MapPin className="w-4 h-4" />
                  <span className="text-sm">{shop.location}</span>
                </div>
                
                <div className="flex items-center gap-2 text-gray-600 mb-4">
                  <Clock className="w-4 h-4" />
                  <span className="text-sm">R$ {shop.priceRange[0]} - R$ {shop.priceRange[1]}</span>
                </div>
                
                <div className="flex flex-wrap gap-2 mb-6">
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
                  <Link 
                    to={`/barbershops?shop=${shop.id}`}
                    className="inline-flex items-center justify-center px-4 py-2 bg-barber-900 text-white rounded-md hover:bg-barber-800 transition-colors"
                  >
                    Ver Detalhes
                  </Link>
                </div>
              </div>
            </div>
          ))}
        </div>

        <div className="text-center mt-12">
          <Link 
            to="/barbershops"
            className="inline-flex items-center justify-center px-6 py-3 bg-barber-900 text-white rounded-md hover:bg-barber-800 transition-colors"
          >
            Ver Todas as Barbearias
          </Link>
        </div>
      </div>
    </section>
  );
};

export default FeaturedShops;
