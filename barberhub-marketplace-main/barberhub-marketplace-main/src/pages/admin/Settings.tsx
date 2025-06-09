import React, { useState } from 'react';
import { useAuth } from '../../contexts/AuthContext';
import { useNavigate } from 'react-router-dom';

const AdminSettings = () => {
  const { user } = useAuth();
  const navigate = useNavigate();
  const [activeTab, setActiveTab] = useState('geral');

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
          <h1 className="text-3xl font-bold text-gray-800">Configurações</h1>
          <button 
            onClick={() => navigate('/admin')}
            className="bg-gray-600 hover:bg-gray-700 text-white py-2 px-4 rounded"
          >
            Voltar ao Dashboard
          </button>
        </div>

        <div className="bg-white rounded-lg shadow">
          {/* Tabs */}
          <div className="border-b border-gray-200">
            <nav className="flex -mb-px">
              <button
                onClick={() => setActiveTab('geral')}
                className={`py-4 px-6 text-sm font-medium ${
                  activeTab === 'geral'
                    ? 'border-b-2 border-gray-600 text-gray-600'
                    : 'text-gray-500 hover:text-gray-700'
                }`}
              >
                Geral
              </button>
              <button
                onClick={() => setActiveTab('notificacoes')}
                className={`py-4 px-6 text-sm font-medium ${
                  activeTab === 'notificacoes'
                    ? 'border-b-2 border-gray-600 text-gray-600'
                    : 'text-gray-500 hover:text-gray-700'
                }`}
              >
                Notificações
              </button>
              <button
                onClick={() => setActiveTab('pagamentos')}
                className={`py-4 px-6 text-sm font-medium ${
                  activeTab === 'pagamentos'
                    ? 'border-b-2 border-gray-600 text-gray-600'
                    : 'text-gray-500 hover:text-gray-700'
                }`}
              >
                Pagamentos
              </button>
              <button
                onClick={() => setActiveTab('seguranca')}
                className={`py-4 px-6 text-sm font-medium ${
                  activeTab === 'seguranca'
                    ? 'border-b-2 border-gray-600 text-gray-600'
                    : 'text-gray-500 hover:text-gray-700'
                }`}
              >
                Segurança
              </button>
            </nav>
          </div>

          {/* Conteúdo das Tabs */}
          <div className="p-6">
            {activeTab === 'geral' && (
              <div className="space-y-6">
                <div>
                  <h3 className="text-lg font-medium text-gray-900">Configurações Gerais</h3>
                  <div className="mt-4 space-y-4">
                    <div>
                      <label className="block text-sm font-medium text-gray-700">
                        Nome da Plataforma
                      </label>
                      <input
                        type="text"
                        className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-gray-500 focus:ring-gray-500"
                        defaultValue="BarberHub"
                      />
                    </div>
                    <div>
                      <label className="block text-sm font-medium text-gray-700">
                        Descrição
                      </label>
                      <textarea
                        className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-gray-500 focus:ring-gray-500"
                        rows={3}
                        defaultValue="Plataforma de agendamento para barbearias e salões de beleza"
                      />
                    </div>
                    <div>
                      <label className="block text-sm font-medium text-gray-700">
                        Fuso Horário
                      </label>
                      <select className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-gray-500 focus:ring-gray-500">
                        <option>America/Sao_Paulo</option>
                        <option>America/New_York</option>
                        <option>Europe/London</option>
                      </select>
                    </div>
                  </div>
                </div>
              </div>
            )}

            {activeTab === 'notificacoes' && (
              <div className="space-y-6">
                <div>
                  <h3 className="text-lg font-medium text-gray-900">Configurações de Notificações</h3>
                  <div className="mt-4 space-y-4">
                    <div className="flex items-center justify-between">
                      <div>
                        <label className="text-sm font-medium text-gray-700">
                          Notificações por Email
                        </label>
                        <p className="text-sm text-gray-500">
                          Receber notificações importantes por email
                        </p>
                      </div>
                      <button className="relative inline-flex h-6 w-11 flex-shrink-0 cursor-pointer rounded-full border-2 border-transparent bg-gray-200 transition-colors duration-200 ease-in-out focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2">
                        <span className="translate-x-5 inline-block h-5 w-5 transform rounded-full bg-white shadow ring-0 transition duration-200 ease-in-out" />
                      </button>
                    </div>
                    <div className="flex items-center justify-between">
                      <div>
                        <label className="text-sm font-medium text-gray-700">
                          Notificações Push
                        </label>
                        <p className="text-sm text-gray-500">
                          Receber notificações no navegador
                        </p>
                      </div>
                      <button className="relative inline-flex h-6 w-11 flex-shrink-0 cursor-pointer rounded-full border-2 border-transparent bg-gray-200 transition-colors duration-200 ease-in-out focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2">
                        <span className="translate-x-0 inline-block h-5 w-5 transform rounded-full bg-white shadow ring-0 transition duration-200 ease-in-out" />
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            )}

            {activeTab === 'pagamentos' && (
              <div className="space-y-6">
                <div>
                  <h3 className="text-lg font-medium text-gray-900">Configurações de Pagamento</h3>
                  <div className="mt-4 space-y-4">
                    <div>
                      <label className="block text-sm font-medium text-gray-700">
                        Taxa de Comissão (%)
                      </label>
                      <input
                        type="number"
                        className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-gray-500 focus:ring-gray-500"
                        defaultValue="10"
                      />
                    </div>
                    <div>
                      <label className="block text-sm font-medium text-gray-700">
                        Moeda Padrão
                      </label>
                      <select className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-gray-500 focus:ring-gray-500">
                        <option>BRL (R$)</option>
                        <option>USD ($)</option>
                        <option>EUR (€)</option>
                      </select>
                    </div>
                  </div>
                </div>
              </div>
            )}

            {activeTab === 'seguranca' && (
              <div className="space-y-6">
                <div>
                  <h3 className="text-lg font-medium text-gray-900">Configurações de Segurança</h3>
                  <div className="mt-4 space-y-4">
                    <div className="flex items-center justify-between">
                      <div>
                        <label className="text-sm font-medium text-gray-700">
                          Autenticação em Duas Etapas
                        </label>
                        <p className="text-sm text-gray-500">
                          Exigir verificação adicional para login
                        </p>
                      </div>
                      <button className="relative inline-flex h-6 w-11 flex-shrink-0 cursor-pointer rounded-full border-2 border-transparent bg-gray-200 transition-colors duration-200 ease-in-out focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2">
                        <span className="translate-x-0 inline-block h-5 w-5 transform rounded-full bg-white shadow ring-0 transition duration-200 ease-in-out" />
                      </button>
                    </div>
                    <div>
                      <label className="block text-sm font-medium text-gray-700">
                        Política de Senhas
                      </label>
                      <select className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-gray-500 focus:ring-gray-500">
                        <option>Mínimo 8 caracteres</option>
                        <option>Mínimo 12 caracteres</option>
                        <option>Mínimo 16 caracteres</option>
                      </select>
                    </div>
                  </div>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminSettings; 