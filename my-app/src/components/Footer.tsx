import { Scissors } from "lucide-react";
import { Link } from "react-router-dom";

const Footer = () => {
  return (
    <footer className="bg-barber-900 text-white pt-16 pb-8 px-6">
      <div className="container mx-auto max-w-7xl">
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-10 mb-16">
          <div>
            <div className="flex items-center gap-2 mb-5">
              <Scissors size={24} />
              <span className="text-xl font-semibold">BarberHub</span>
            </div>
            <p className="text-white/70 mb-6">
              Conectando clientes com barbearias premium para experiências excepcionais de cuidados pessoais.
            </p>
          </div>
          
          <div>
            <h3 className="text-lg font-semibold mb-5">Para Clientes</h3>
            <ul className="space-y-3">
              <li>
                <Link to="/barbershops" className="text-white/70 hover:text-white transition-colors">
                  Encontrar Barbearias
                </Link>
              </li>
              <li><a href="#" className="text-white/70 hover:text-white transition-colors">Agendar Horários</a></li>
              <li><a href="#" className="text-white/70 hover:text-white transition-colors">Explorar Serviços</a></li>
              <li><a href="#" className="text-white/70 hover:text-white transition-colors">Vale-Presente</a></li>
              <li><a href="#" className="text-white/70 hover:text-white transition-colors">Avaliações</a></li>
            </ul>
          </div>
          
          <div>
            <h3 className="text-lg font-semibold mb-5">Para Barbeiros</h3>
            <ul className="space-y-3">
              <li>
                <Link to="/barber-signup" className="text-white/70 hover:text-white transition-colors">
                  Junte-se ao BarberHub
                </Link>
              </li>
            </ul>
          </div>
          
          <div>
            <h3 className="text-lg font-semibold mb-5">Empresa</h3>
            <ul className="space-y-3">
              <li>
                <Link to="/about" className="text-white/70 hover:text-white transition-colors">
                  Sobre Nós
                </Link>
              </li>
            </ul>
          </div>
        </div>
        
        <div className="border-t border-white/10 pt-8 flex flex-col md:flex-row justify-between gap-4 items-center">
          <p className="text-white/50 text-sm">
            © {new Date().getFullYear()} BarberHub. Todos os direitos reservados.
          </p>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
