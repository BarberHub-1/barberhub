import { Button } from "@/components/ui/button";
import ShopCard from "./ShopCard";
import { FadeIn, StaggeredContainer } from "./Transitions";
import { Link } from "react-router-dom";

// Mock data
const featuredShops = [
  {
    id: "1",
    name: "The Classic Cut",
    image: "https://images.unsplash.com/photo-1562322140-8baeececf3df?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2069&q=80",
    location: "Centro, São Paulo",
    rating: 4.8,
    reviews: 124,
    services: ["Corte de Cabelo", "Barba", "Estilização"]
  },
  {
    id: "2",
    name: "Modern Barber",
    image: "https://img.freepik.com/fotos-gratis/cadeiras-na-barbearia-masculina-verde-em-estilo-retro_627829-8284.jpg?t=st=1745341620~exp=1745345220~hmac=52dc1a3fdbcbfebf95474eb3df0d1b43ce1c7bcbecec6aed323691375577eb0b&w=996",
    location: "Jardins, São Paulo",
    rating: 4.9,
    reviews: 98,
    services: ["Corte de Cabelo", "Barba", "Tratamento Facial"]
  },
  {
    id: "3",
    name: "Elite Barbershop",
    image: "https://images.unsplash.com/photo-1534297635766-a262cdcb8ee4?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80",
    location: "Vila Madalena, São Paulo",
    rating: 4.7,
    reviews: 156,
    services: ["Corte de Cabelo", "Barba", "Coloração"]
  },
  {
    id: "4",
    name: "Premium Cuts",
    image: "https://images.unsplash.com/photo-1562322140-8baeececf3df?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2069&q=80",
    location: "Moema, São Paulo",
    rating: 4.9,
    reviews: 87,
    services: ["Corte de Cabelo", "Barba", "Estilização"]
  }
];

const FeaturedShops = () => {
  return (
    <section id="featured" className="py-20 px-6">
      <div className="container mx-auto max-w-7xl">
        <FadeIn>
          <div className="flex flex-col md:flex-row md:items-end justify-between mb-12 gap-4">
            <div>
              <span className="text-sm font-medium text-barber-500 mb-2 block">BARBEARIAS EM DESTAQUE</span>
              <h2 className="text-3xl md:text-4xl font-bold text-barber-900">Populares perto de você</h2>
            </div>
            <Button variant="outline" className="self-start md:self-auto" asChild>
              <Link to="/barbershops">Ver todas as barbearias</Link>
            </Button>
          </div>
        </FadeIn>
        
        <StaggeredContainer className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 md:gap-8">
          {featuredShops.map((shop) => (
            <FadeIn key={shop.id}>
              <ShopCard {...shop} />
            </FadeIn>
          ))}
        </StaggeredContainer>
      </div>
    </section>
  );
};

export default FeaturedShops;
