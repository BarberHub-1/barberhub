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
    nomeEstabelecimento: string;
    nomeProprietario: string;
    endereco: string;
    bairro?: string;
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
    servicos: (string | {
        id: number;
        descricao: string;
        preco: number;
        duracaoMinutos: number;
        tipo: string;
    })[];
    notaMedia?: number;
    quantidadeAvaliacoes?: number;
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