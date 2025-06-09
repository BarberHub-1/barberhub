import React, { useState, useEffect } from 'react';
import { useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { estabelecimentoService } from '../services/estabelecimento.service';
import { FaSearch, FaMapMarkerAlt, FaStar, FaFilter } from 'react-icons/fa';
import { Spinner } from '../components/Spinner';
import { toast } from 'react-toastify';
import Navigation from '../components/Navigation';

interface Servico {
  id: number;
  tipo: string;
  descricao: string;
  preco: number;
  duracaoMinutos: number;
}

interface BarberShop {
  id: number;
  nomeEstabelecimento: string;
  nomeProprietario: string;
  endereco: string;
  cidade: string;
  cep: string;
  telefone: string;
  foto?: string;
  status: string;
  descricao?: string;
  horario: {
    id: number;
    diaSemana: string;
    horarioAbertura: string;
    horarioFechamento: string;
  }[];
  servicos: Servico[];
}

export default function Barbershops() {
  const navigate = useNavigate();
  const { user } = useAuth();
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCity, setSelectedCity] = useState('');
  const [selectedService, setSelectedService] = useState('');
  const [selectedLocation, setSelectedLocation] = useState('');
  const [selectedRating, setSelectedRating] = useState('');
  const [priceRange, setPriceRange] = useState([0, 200]);

  const { data: barbershops, isLoading, error } = useQuery({
    queryKey: ['barbershops'],
    queryFn: async () => {
      try {
        const response = await estabelecimentoService.getAll();
        console.log('Dados recebidos do backend:', response);
        
        // Converter os serviços de string para objeto
        const barbershopsProcessados = response.map(shop => ({
          ...shop,
          servicos: shop.servicos.map(servicoStr => {
            // Garantir que estamos lidando com uma string
            const servicoString = typeof servicoStr === 'string' ? servicoStr : JSON.stringify(servicoStr);
            
            const servicoObj = {
              id: parseInt(servicoString.match(/id=(\d+)/)?.[1] || '0'),
              tipo: servicoString.match(/tipo=([^,]+)/)?.[1] || '',
              descricao: servicoString.match(/descricao=([^,]+)/)?.[1] || '',
              preco: parseFloat(servicoString.match(/preco=([^,]+)/)?.[1] || '0'),
              duracaoMinutos: parseInt(servicoString.match(/duracaoMinutos=(\d+)/)?.[1] || '0')
            };
            return servicoObj;
          })
        }));
        
        console.log('Barbearias processadas:', barbershopsProcessados);
        return barbershopsProcessados as BarberShop[];
      } catch (error) {
        console.error('Erro ao buscar barbearias:', error);
        throw error;
      }
    }
  });

  // Extrair cidades únicas das barbearias
  const uniqueCities = React.useMemo(() => {
    if (!barbershops) return [];
    return Array.from(new Set(barbershops.map(shop => shop.cidade))).sort();
  }, [barbershops]);

  useEffect(() => {
    if (user?.tipo === 'ESTABELECIMENTO') {
      navigate('/dashboard');
    }
  }, [user, navigate]);

  useEffect(() => {
    console.log('Estado atual das barbearias:', barbershops);
    if (barbershops && barbershops.length > 0) {
      console.log('Primeira barbearia:', barbershops[0]);
      console.log('Serviços da primeira barbearia:', barbershops[0].servicos);
    }
  }, [barbershops]);

  if (isLoading) {
    return (
      <div className="flex justify-center items-center min-h-screen">
        <Spinner />
      </div>
    );
  }

  if (error) {
    console.error('Erro ao carregar barbearias:', error);
    return (
      <div className="flex justify-center items-center min-h-screen">
        <p className="text-red-500">Erro ao carregar as barbearias. Tente novamente mais tarde.</p>
      </div>
    );
  }

  const filteredBarbershops = barbershops?.filter(shop => {
    if (!shop) return false;

    // Se não houver filtros ativos, retorna true
    if (!searchTerm && !selectedCity && !selectedService && !selectedLocation && !selectedRating) {
      return true;
    }

    // Verifica cada filtro apenas se estiver ativo
    const matchesSearch = !searchTerm || 
      (shop.nomeEstabelecimento?.toLowerCase().includes(searchTerm.toLowerCase()) ||
       shop.endereco?.toLowerCase().includes(searchTerm.toLowerCase()));

    const matchesCity = !selectedCity || shop.cidade === selectedCity;
    
    const matchesService = !selectedService || 
      (shop.servicos && shop.servicos.some(service => service.tipo === selectedService));
    
    const matchesLocation = !selectedLocation || shop.cidade === selectedLocation;
    
    const matchesRating = !selectedRating || shop.status === 'APROVADO';

    // Retorna true se todos os filtros ativos corresponderem
    return matchesSearch && matchesCity && matchesService && matchesLocation && matchesRating;
  });

  console.log('Barbearias filtradas:', filteredBarbershops);

  return (
    <div className="min-h-screen bg-gray-50">
      <Navigation />
      <div className="container mx-auto px-4 py-8 mt-20">
        <div className="mb-8">
          <h1 className="text-3xl font-bold text-gray-800 mb-4">Encontre sua Barbearia</h1>
          
          {/* Barra de Pesquisa */}
          <div className="relative">
            <input
              type="text"
              placeholder="Buscar por nome ou endereço..."
              className="w-full p-4 pl-12 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
            <FaSearch className="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400" />
          </div>

          {/* Filtros */}
          <div className="mt-4 grid grid-cols-1 md:grid-cols-4 gap-4">
            <select
              className="p-2 border rounded-lg"
              value={selectedService}
              onChange={(e) => setSelectedService(e.target.value)}
            >
              <option value="">Todos os Serviços</option>
              <option value="CORTE_DE_CABELO">Corte de Cabelo</option>
              <option value="BARBA">Barba</option>
              <option value="HIDRATACAO">Hidratação</option>
              <option value="LUZES">Luzes</option>
              <option value="SOBRANCELHA">Sobrancelha</option>
            </select>

            <select
              className="p-2 border rounded-lg"
              value={selectedLocation}
              onChange={(e) => setSelectedLocation(e.target.value)}
            >
              <option value="">Todas as Localizações</option>
              {uniqueCities.map((city) => (
                <option key={city} value={city}>
                  {city}
                </option>
              ))}
            </select>

            <select
              className="p-2 border rounded-lg"
              value={selectedRating}
              onChange={(e) => setSelectedRating(e.target.value)}
            >
              <option value="">Todas as Avaliações</option>
              <option value="4">4+ Estrelas</option>
              <option value="3">3+ Estrelas</option>
              <option value="2">2+ Estrelas</option>
            </select>

            <div className="flex items-center space-x-2">
              <span>Preço:</span>
              <input
                type="range"
                min="0"
                max="200"
                value={priceRange[1]}
                onChange={(e) => setPriceRange([0, parseInt(e.target.value)])}
                className="w-full"
              />
              <span>R$ {priceRange[1]}</span>
            </div>
          </div>
        </div>

        {/* Lista de Barbearias */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {filteredBarbershops?.map((shop) => (
            <div
              key={shop.id}
              className="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow duration-300"
            >
              <div className="relative h-48">
                <img
                  src={shop.foto || 'https://placehold.co/300x200/e2e8f0/1e293b?text=Barbearia'}
                  alt={shop.nomeEstabelecimento}
                  className="w-full h-full object-cover"
                  onError={(e) => {
                    e.currentTarget.src = 'https://placehold.co/300x200/e2e8f0/1e293b?text=Barbearia';
                  }}
                />
                {shop.status === 'APROVADO' && (
                  <div className="absolute top-2 right-2 bg-green-500 text-white px-2 py-1 rounded-full text-sm">
                    Verificado
                  </div>
                )}
              </div>

              <div className="p-4">
                <h2 className="text-xl font-semibold text-gray-800 mb-2">{shop.nomeEstabelecimento}</h2>
                
                <div className="flex items-center text-gray-600 mb-2">
                  <FaMapMarkerAlt className="mr-2" />
                  <span>{shop.endereco}, {shop.cidade}</span>
                </div>

                <div className="flex items-center text-gray-600 mb-4">
                  <FaStar className="mr-2 text-yellow-400" />
                  <span>4.5 (120 avaliações)</span>
                </div>

                <div className="flex flex-wrap gap-2 mb-4">
                  {shop.servicos && shop.servicos.length > 0 ? (
                    <>
                      {shop.servicos.slice(0, 3).map((service) => (
                        <span
                          key={`${shop.id}-service-${service.id}`}
                          className="bg-blue-100 text-blue-800 text-sm px-2 py-1 rounded"
                        >
                          {service.descricao} - R$ {service.preco.toFixed(2)}
                        </span>
                      ))}
                      {shop.servicos.length > 3 && (
                        <span 
                          key={`${shop.id}-more-services-${shop.servicos.length}`}
                          className="bg-gray-100 text-gray-800 text-sm px-2 py-1 rounded"
                        >
                          +{shop.servicos.length - 3} serviços
                        </span>
                      )}
                    </>
                  ) : (
                    <span className="text-gray-500 text-sm">Nenhum serviço cadastrado</span>
                  )}
                </div>

                <div className="flex justify-between items-center mb-4">
                  <div className="text-sm text-gray-600">
                    <span className="font-medium">Horário:</span>{' '}
                    {shop.horario?.[0]?.horarioAbertura ? (
                      `${shop.horario[0].horarioAbertura} - ${shop.horario[0].horarioFechamento}`
                    ) : (
                      'Horário não definido'
                    )}
                  </div>
                  <div className="text-sm text-gray-600">
                    <span className="font-medium">Duração:</span>{' '}
                    {shop.servicos?.[0]?.duracaoMinutos ? (
                      `${shop.servicos[0].duracaoMinutos}min`
                    ) : (
                      'Não definida'
                    )}
                  </div>
                </div>

                <button
                  onClick={() => navigate(`/barbershops/${shop.id}`)}
                  className="w-full bg-gray-600 text-white py-2 rounded-lg hover:bg-gray-700 transition-colors duration-300"
                >
                  Ver Detalhes
                </button>
              </div>
            </div>
          ))}
        </div>

        {filteredBarbershops?.length === 0 && (
          <div className="text-center py-8">
            <p className="text-gray-500 text-lg">Nenhuma barbearia encontrada com os filtros selecionados.</p>
          </div>
        )}
      </div>
    </div>
  );
} 