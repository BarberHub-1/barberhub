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

interface AgendamentoResponse {
  id: number;
  dataHora: string;
  statusAgendamento?: 'AGENDADA' | 'CANCELADA' | 'CONCLUIDA';
  status?: 'AGENDADA' | 'CANCELADA' | 'CONCLUIDA';
  clienteId: number;
  estabelecimentoId: number;
  estabelecimentoNome: string;
  servicos: number[];
  servicosNomes: string[];
}

// Interceptor para mapear o status do agendamento
api.interceptors.response.use((response) => {
  if (response.data) {
    if (Array.isArray(response.data)) {
      response.data = response.data.map((agendamento: AgendamentoResponse) => ({
        ...agendamento,
        status: agendamento.statusAgendamento || agendamento.status
      }));
    } else if ((response.data as AgendamentoResponse).statusAgendamento) {
      (response.data as AgendamentoResponse).status = (response.data as AgendamentoResponse).statusAgendamento;
    }
  }
  return response;
});

export interface Agendamento {
  id: number;
  // Data e hora do agendamento no formato ISO 8601
  dataHora: string;
  status: 'AGENDADA' | 'CANCELADA' | 'CONCLUIDA';
  statusAgendamento?: 'AGENDADA' | 'CANCELADA' | 'CONCLUIDA';
  clienteId: number;
  estabelecimentoId: number;
  estabelecimentoNome: string;
  servicos: number[]; // Lista de IDs de serviço
  servicosNomes: string[]; // Lista de nomes dos serviços
}

export interface AgendamentoPayload {
  clienteId: number;
  estabelecimentoId: number;
  servicos: number[]; // Lista de IDs de serviço
  dataHora: string; // Formato ISO 8601
}

export const agendamentoService = {
  // Buscar todos os agendamentos do cliente logado
  getAgendamentosCliente: async (): Promise<Agendamento[]> => {
    const clienteId = localStorage.getItem('userId');
    if (!clienteId) {
      throw new Error('ID do cliente não encontrado');
    }
    const response = await api.get<AgendamentoResponse[]>(`/api/agendamentos/cliente/${clienteId}`);
    return response.data.map((agendamento) => ({
      ...agendamento,
      status: (agendamento.statusAgendamento || agendamento.status) as 'AGENDADA' | 'CANCELADA' | 'CONCLUIDA'
    }));
  },

  // Criar novo agendamento
  criarAgendamento: async (data: AgendamentoPayload): Promise<Agendamento> => {
    const response = await api.post<AgendamentoResponse>('/api/agendamentos', data);
    return {
      ...response.data,
      status: (response.data.statusAgendamento || response.data.status) as 'AGENDADA' | 'CANCELADA' | 'CONCLUIDA'
    };
  },

  // Cancelar agendamento
  cancelarAgendamento: async (id: number): Promise<void> => {
    await api.put(`/api/agendamentos/${id}/cancelar`);
  },

  // Concluir agendamento
  concluirAgendamento: async (id: number): Promise<void> => {
    await api.put(`/api/agendamentos/${id}/status?status=CONCLUIDA`);
  }
}; 