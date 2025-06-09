import { useState } from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { MoreHorizontal, Search } from "lucide-react";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import api from "@/lib/axios";
import { useToast } from "@/hooks/use-toast";

interface Barbershop {
  id: number;
  nome: string;
  email: string;
  status: "PENDENTE" | "APROVADO" | "REJEITADO"; // Assumindo esses status
  dataCadastro: string;
}

const Barbershops = () => {
  const [searchTerm, setSearchTerm] = useState("");
  const queryClient = useQueryClient();
  const { toast } = useToast();

  // Buscar barbearias do backend
  const { data: barbershops, isLoading, error } = useQuery<Barbershop[]>({
    queryKey: ["barbershops"],
    queryFn: async () => {
      const response = await api.get<Barbershop[]>("/barbershops");
      return response.data;
    },
  });

  // Mutação para aprovar barbearia
  const approveBarbershopMutation = useMutation({
    mutationFn: async (id: number) => {
      await api.put(`/barbershops/${id}/approve`);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["barbershops"] });
      toast({
        title: "Barbearia Aprovada!",
        description: "O status da barbearia foi alterado para APROVADO.",
      });
    },
    onError: (err) => {
      toast({
        title: "Erro ao Aprovar",
        description: "Não foi possível aprovar a barbearia. Tente novamente.",
        variant: "destructive",
      });
      console.error("Erro ao aprovar barbearia:", err);
    },
  });

  const handleApprove = (id: number) => {
    approveBarbershopMutation.mutate(id);
  };

  const filteredBarbershops = (barbershops || []).filter(
    (shop) =>
      shop.nome.toLowerCase().includes(searchTerm.toLowerCase()) ||
      shop.email.toLowerCase().includes(searchTerm.toLowerCase())
  );

  if (isLoading) return <div>Carregando barbearias...</div>;
  if (error) return <div>Erro ao carregar barbearias: {error.message}</div>;

  return (
    <div className="space-y-8">
      <div>
        <h1 className="text-3xl font-bold tracking-tight">Barbearias</h1>
        <p className="text-muted-foreground">
          Gerencie as barbearias cadastradas na plataforma
        </p>
      </div>

      <div className="flex items-center justify-between">
        <div className="flex items-center gap-2">
          <div className="relative">
            <Search className="absolute left-2 top-2.5 h-4 w-4 text-muted-foreground" />
            <Input
              placeholder="Buscar barbearias..."
              className="pl-8"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </div>
        </div>
      </div>

      <div className="rounded-md border">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>Nome</TableHead>
              <TableHead>Email</TableHead>
              <TableHead>Status</TableHead>
              <TableHead>Data de Cadastro</TableHead>
              <TableHead className="w-[50px]"></TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {filteredBarbershops.map((shop) => (
              <TableRow key={shop.id}>
                <TableCell>{shop.nome}</TableCell>
                <TableCell>{shop.email}</TableCell>
                <TableCell>{shop.status}</TableCell>
                <TableCell>{shop.dataCadastro}</TableCell>
                <TableCell>
                  <DropdownMenu>
                    <DropdownMenuTrigger asChild>
                      <Button variant="ghost" className="h-8 w-8 p-0 hover:bg-gray-100">
                        <MoreHorizontal className="h-4 w-4" />
                      </Button>
                    </DropdownMenuTrigger>
                    <DropdownMenuContent align="end">
                      {shop.status === "PENDENTE" && (
                        <DropdownMenuItem onClick={() => handleApprove(shop.id)} className="hover:bg-gray-100">
                          Aprovar Cadastro
                        </DropdownMenuItem>
                      )}
                      <DropdownMenuItem className="hover:bg-gray-100">Ver Detalhes</DropdownMenuItem>
                    </DropdownMenuContent>
                  </DropdownMenu>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </div>
    </div>
  );
};

export default Barbershops; 