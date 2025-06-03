import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json'
  }
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export interface Agendamento {
  id: number;
  data: string;
  horario: string;
  status: 'AGENDADO' | 'CONCLUIDO' | 'CANCELADO';
  servico: {
    id: number;
    descricao: string;
    preco: number;
    duracaoMinutos: number;
  };
  estabelecimento: {
    id: number;
    nomeEstabelecimento: string;
    endereco: string;
    cidade: string;
  };
}

export const agendamentoService = {
  // Buscar todos os agendamentos do cliente logado
  getAgendamentosCliente: async (): Promise<Agendamento[]> => {
    const clienteId = localStorage.getItem('userId');
    if (!clienteId) {
      throw new Error('ID do cliente não encontrado');
    }
    const response = await api.get(`/api/agendamentos/cliente/${clienteId}`);
    return response.data;
  },

  // Criar novo agendamento
  criarAgendamento: async (data: {
    servicoId: number;
    estabelecimentoId: number;
    data: string;
    horario: string;
  }): Promise<Agendamento> => {
    const response = await api.post('/api/agendamentos', data);
    return response.data;
  },

  // Cancelar agendamento
  cancelarAgendamento: async (id: number): Promise<void> => {
    await api.put(`/api/agendamentos/${id}/cancelar`);
  }
}; 