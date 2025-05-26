import { Button } from "@/components/ui/button";
import { FadeIn } from "./Transitions";
import { Search as SearchIcon, MapPin, Calendar, Scissors } from "lucide-react";

const Search = () => {
  return (
    <section className="py-20 px-6">
      <div className="container mx-auto max-w-7xl">
        <FadeIn className="bg-barber-900 text-white rounded-2xl p-12 relative overflow-hidden">
          <div className="absolute top-0 right-0 w-1/3 h-full opacity-10">
            <div className="w-full h-full bg-[radial-gradient(circle_at_center,_var(--tw-gradient-stops))] from-white/40 to-transparent transform rotate-45" />
          </div>
          
          <div className="relative z-10 max-w-3xl">
            <h2 className="text-3xl md:text-4xl font-bold mb-6">Encontre a barbearia perfeita perto de você</h2>
            <p className="text-white/80 mb-8 text-lg">
              Pesquise em nossa lista curada de barbearias profissionais e agende seu próximo horário em segundos.
            </p>
            
            <div className="bg-white/10 backdrop-blur-md p-1 rounded-lg flex flex-col md:flex-row gap-2">
              <div className="flex-1 flex items-center bg-white/10 rounded-md px-4 py-3">
                <Scissors size={18} className="text-white/60 mr-3" />
                <input
                  type="text"
                  placeholder="Serviço ou nome da barbearia"
                  className="w-full bg-transparent text-white placeholder-white/60 outline-none"
                />
              </div>
              
              <div className="flex-1 flex items-center bg-white/10 rounded-md px-4 py-3">
                <MapPin size={18} className="text-white/60 mr-3" />
                <input
                  type="text"
                  placeholder="Localização"
                  className="w-full bg-transparent text-white placeholder-white/60 outline-none"
                />
              </div>
              
              <div className="flex-1 flex items-center bg-white/10 rounded-md px-4 py-3 md:hidden">
                <Calendar size={18} className="text-white/60 mr-3" />
                <input
                  type="text"
                  placeholder="Data"
                  className="w-full bg-transparent text-white placeholder-white/60 outline-none"
                />
              </div>
              
              <Button size="lg" className="bg-white text-barber-900 hover:bg-white/90">
                <SearchIcon size={18} className="mr-2" />
                Pesquisar
              </Button>
            </div>
          </div>
        </FadeIn>
      </div>
    </section>
  );
};

export default Search;
