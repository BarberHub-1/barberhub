import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Users, Building2, Calendar, TrendingUp } from "lucide-react";

const Dashboard = () => {
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
    <div className="space-y-8">
      <div>
        <h1 className="text-3xl font-bold tracking-tight">Dashboard</h1>
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
  );
};

export default Dashboard; 