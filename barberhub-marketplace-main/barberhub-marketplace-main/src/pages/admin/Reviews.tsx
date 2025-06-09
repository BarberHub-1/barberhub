import React from 'react';
import { useAuth } from '../../contexts/AuthContext';
import { useNavigate } from 'react-router-dom';

const AdminReviews = () => {
  const { user } = useAuth();
  const navigate = useNavigate();

  if (!user || user.role !== 'ADMIN') {
    return (
      <div className="min-h-screen bg-gray-50">
        <div className="container mx-auto px-4 py-8">
          <div className="text-center">
            <h1 className="text-2xl font-bold text-red-600">Acesso Negado</h1>
            <p className="mt-2 text-gray-600">Você não tem permissão para acessar esta área.</p>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="container mx-auto px-4 py-8">
        <div className="flex justify-between items-center mb-8">
          <h1 className="text-3xl font-bold text-gray-800">Gerenciamento de Avaliações</h1>
          <button 
            onClick={() => navigate('/admin')}
            className="bg-gray-600 hover:bg-gray-700 text-white py-2 px-4 rounded"
          >
            Voltar ao Dashboard
          </button>
        </div>

        <div className="bg-white rounded-lg shadow">
          <div className="p-6">
            <div className="flex justify-between items-center mb-6">
              <div className="flex gap-4">
                <input
                  type="text"
                  placeholder="Buscar avaliação..."
                  className="border rounded px-4 py-2 w-64"
                />
                <select className="border rounded px-4 py-2">
                  <option value="">Todas as notas</option>
                  <option value="5">5 estrelas</option>
                  <option value="4">4 estrelas</option>
                  <option value="3">3 estrelas</option>
                  <option value="2">2 estrelas</option>
                  <option value="1">1 estrela</option>
                </select>
              </div>
            </div>

            <table className="w-full">
              <thead>
                <tr className="border-b">
                  <th className="text-left py-3 px-4">Estabelecimento</th>
                  <th className="text-left py-3 px-4">Cliente</th>
                  <th className="text-left py-3 px-4">Nota</th>
                  <th className="text-left py-3 px-4">Data</th>
                  <th className="text-left py-3 px-4">Ações</th>
                </tr>
              </thead>
              <tbody>
                <tr className="border-b">
                  <td className="py-3 px-4">Barbearia do João</td>
                  <td className="py-3 px-4">Maria Silva</td>
                  <td className="py-3 px-4">
                    <div className="flex items-center">
                      <span className="text-yellow-400">★★★★★</span>
                      <span className="ml-2">5.0</span>
                    </div>
                  </td>
                  <td className="py-3 px-4">01/03/2024</td>
                  <td className="py-3 px-4">
                    <div className="flex gap-2">
                      <button className="text-blue-600 hover:text-blue-800">Ver Detalhes</button>
                      <button className="text-red-600 hover:text-red-800">Remover</button>
                    </div>
                  </td>
                </tr>
                <tr className="border-b">
                  <td className="py-3 px-4">Salão da Maria</td>
                  <td className="py-3 px-4">João Santos</td>
                  <td className="py-3 px-4">
                    <div className="flex items-center">
                      <span className="text-yellow-400">★★★★</span>
                      <span className="ml-2">4.0</span>
                    </div>
                  </td>
                  <td className="py-3 px-4">28/02/2024</td>
                  <td className="py-3 px-4">
                    <div className="flex gap-2">
                      <button className="text-blue-600 hover:text-blue-800">Ver Detalhes</button>
                      <button className="text-red-600 hover:text-red-800">Remover</button>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>

            <div className="mt-6 flex justify-between items-center">
              <div className="text-gray-600">
                Mostrando 1-2 de 2 resultados
              </div>
              <div className="flex gap-2">
                <button className="border rounded px-3 py-1 disabled:opacity-50" disabled>
                  Anterior
                </button>
                <button className="border rounded px-3 py-1 disabled:opacity-50" disabled>
                  Próxima
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminReviews; 