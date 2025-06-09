import React from 'react';
import { useAuth } from '../../contexts/AuthContext';
import { useNavigate } from 'react-router-dom';
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Users, Building2, Calendar, TrendingUp } from "lucide-react";

const AdminDashboard = () => {
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

  // Dados mockados para exemplo
  const stats = [
    {
      title: "Total de Usuários",
      value: "1.234",
      icon: <Users className="h-4 w-4 text-muted-foreground" />,
      description: "+12% em relação ao mês anterior",
    },
    {
      title: "Barbearias Ativas",
      value: "89",
      icon: <Building2 className="h-4 w-4 text-muted-foreground" />,
      description: "+5 novas este mês",
    },
    {
      title: "Agendamentos",
      value: "456",
      icon: <Calendar className="h-4 w-4 text-muted-foreground" />,
      description: "Hoje",
    },
    {
      title: "Taxa de Crescimento",
      value: "23%",
      icon: <TrendingUp className="h-4 w-4 text-muted-foreground" />,
      description: "Em relação ao mês anterior",
    },
  ];

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="container mx-auto px-4 py-8">
        <h1 className="text-3xl font-bold text-gray-800 mb-8">Painel Administrativo</h1>
        
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {/* Card de Estabelecimentos */}
          <div className="bg-white rounded-lg shadow p-6">
            <h2 className="text-xl font-semibold text-gray-800 mb-4">Estabelecimentos</h2>
            <p className="text-gray-600 mb-4">Gerencie os estabelecimentos cadastrados no sistema.</p>
            <button 
              onClick={() => navigate('/admin/barbershops')}
              className="w-full bg-gray-600 hover:bg-gray-700 text-white py-2 px-4 rounded"
            >
              Gerenciar Estabelecimentos
            </button>
          </div>

          {/* Card de Usuários */}
          <div className="bg-white rounded-lg shadow p-6">
            <h2 className="text-xl font-semibold text-gray-800 mb-4">Usuários</h2>
            <p className="text-gray-600 mb-4">Gerencie os usuários do sistema.</p>
            <button 
              onClick={() => navigate('/admin/users')}
              className="w-full bg-gray-600 hover:bg-gray-700 text-white py-2 px-4 rounded"
            >
              Gerenciar Usuários
            </button>
          </div>

          {/* Card de Relatórios */}
          <div className="bg-white rounded-lg shadow p-6">
            <h2 className="text-xl font-semibold text-gray-800 mb-4">Relatórios</h2>
            <p className="text-gray-600 mb-4">Acesse relatórios e estatísticas do sistema.</p>
            <button 
              onClick={() => navigate('/admin/reports')}
              className="w-full bg-gray-600 hover:bg-gray-700 text-white py-2 px-4 rounded"
            >
              Ver Relatórios
            </button>
          </div>

          {/* Card de Configurações */}
          <div className="bg-white rounded-lg shadow p-6">
            <h2 className="text-xl font-semibold text-gray-800 mb-4">Configurações</h2>
            <p className="text-gray-600 mb-4">Configure as opções do sistema.</p>
            <button 
              onClick={() => navigate('/admin/settings')}
              className="w-full bg-gray-600 hover:bg-gray-700 text-white py-2 px-4 rounded"
            >
              Configurações
            </button>
          </div>

          {/* Card de Serviços */}
          <div className="bg-white rounded-lg shadow p-6">
            <h2 className="text-xl font-semibold text-gray-800 mb-4">Serviços</h2>
            <p className="text-gray-600 mb-4">Gerencie os serviços disponíveis no sistema.</p>
            <button 
              onClick={() => navigate('/admin/services')}
              className="w-full bg-gray-600 hover:bg-gray-700 text-white py-2 px-4 rounded"
            >
              Gerenciar Serviços
            </button>
          </div>

          {/* Card de Avaliações */}
          <div className="bg-white rounded-lg shadow p-6">
            <h2 className="text-xl font-semibold text-gray-800 mb-4">Avaliações</h2>
            <p className="text-gray-600 mb-4">Gerencie as avaliações dos estabelecimentos.</p>
            <button 
              onClick={() => navigate('/admin/reviews')}
              className="w-full bg-gray-600 hover:bg-gray-700 text-white py-2 px-4 rounded"
            >
              Gerenciar Avaliações
            </button>
          </div>
        </div>

        <div className="space-y-8 mt-8">
          <div>
            <h2 className="text-2xl font-bold tracking-tight">Dashboard</h2>
            <p className="text-muted-foreground">
              Bem-vindo ao painel administrativo do BarberHub
            </p>
          </div>

          <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
            {stats.map((stat) => (
              <Card key={stat.title}>
                <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
                  <CardTitle className="text-sm font-medium">
                    {stat.title}
                  </CardTitle>
                  {stat.icon}
                </CardHeader>
                <CardContent>
                  <div className="text-2xl font-bold">{stat.value}</div>
                  <p className="text-xs text-muted-foreground">
                    {stat.description}
                  </p>
                </CardContent>
              </Card>
            ))}
          </div>

          <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-7">
            <Card className="col-span-4">
              <CardHeader>
                <CardTitle>Visão Geral</CardTitle>
              </CardHeader>
              <CardContent>
                <p className="text-sm text-muted-foreground">
                  Aqui você encontrará gráficos e análises detalhadas sobre o desempenho da plataforma.
                </p>
              </CardContent>
            </Card>

            <Card className="col-span-3">
              <CardHeader>
                <CardTitle>Atividades Recentes</CardTitle>
              </CardHeader>
              <CardContent>
                <p className="text-sm text-muted-foreground">
                  Lista das últimas atividades realizadas na plataforma.
                </p>
              </CardContent>
            </Card>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminDashboard; 