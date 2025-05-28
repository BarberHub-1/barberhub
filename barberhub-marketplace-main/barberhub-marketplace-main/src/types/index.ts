export interface Usuario {
    id: number;
    email: string;
    senha: string;
}

export interface Cliente extends Usuario {
    nome: string;
    telefone: string;
    foto?: string;
}

export interface Profissional extends Usuario {
    nome: string;
    telefone: string;
    foto?: string;
    especialidades: string[];
}

export interface Estabelecimento {
    id: number;
    nome: string;
    endereco: string;
    telefone: string;
    foto?: string;
    status: 'ATIVO' | 'AGUARDANDO_APROVACAO' | 'REJEITADO' | 'BLOQUEADO';
    horariosFuncionamento: HorarioFuncionamento[];
}

export interface HorarioFuncionamento {
    id: number;
    diaSemana: string;
    horarioAbertura: string;
    horarioFechamento: string;
}

export interface Servico {
    id: number;
    nome: string;
    descricao: string;
    preco: number;
    duracao: number;
    estabelecimentoId: number;
}

export interface Agendamento {
    id: number;
    clienteId: number;
    profissionalId: number;
    servicoId: number;
    data: string;
    horario: string;
    status: 'AGENDADO' | 'CONFIRMADO' | 'CANCELADO' | 'CONCLUIDO';
}

export interface Avaliacao {
    id: number;
    clienteId: number;
    estabelecimentoId: number;
    nota: number;
    comentario: string;
    data: string;
} 