import api from '../lib/axios';
import { Estabelecimento } from '../types';

export const estabelecimentoService = {
    async getAll() {
        const response = await api.get<Estabelecimento[]>('/api/estabelecimentos');
        return response.data.map(estabelecimento => ({
            ...estabelecimento,
            foto: estabelecimento.foto ? `data:image/jpeg;base64,${estabelecimento.foto}` : undefined
        }));
    },

    async getById(id: number) {
        const response = await api.get<Estabelecimento>(`/api/estabelecimentos/${id}`);
        return {
            ...response.data,
            foto: response.data.foto ? `data:image/jpeg;base64,${response.data.foto}` : undefined
        };
    },

    async create(estabelecimento: Partial<Estabelecimento>) {
        const response = await api.post<Estabelecimento>('/api/estabelecimentos', estabelecimento);
        return response.data;
    },

    async update(id: number, estabelecimento: Partial<Estabelecimento>) {
        const response = await api.put<Estabelecimento>(`/api/estabelecimentos/${id}`, estabelecimento);
        return response.data;
    },

    async delete(id: number) {
        await api.delete(`/api/estabelecimentos/${id}`);
    },

    async aprovar(id: number) {
        console.log('Chamando API para aprovar estabelecimento ID:', id);
        const response = await api.patch<Estabelecimento>(`/api/estabelecimentos/${id}/status`, { status: 'APROVADO' });
        console.log('Resposta da API:', response.data);
        return response.data;
    },

    async rejeitar(id: number) {
        console.log('Chamando API para rejeitar estabelecimento ID:', id);
        const response = await api.patch<Estabelecimento>(`/api/estabelecimentos/${id}/status`, { status: 'REJEITADO' });
        console.log('Resposta da API:', response.data);
        return response.data;
    },

    async desativar(id: number) {
        console.log('Chamando API para desativar estabelecimento ID:', id);
        const response = await api.patch<Estabelecimento>(`/api/estabelecimentos/${id}/status`, { status: 'REJEITADO' });
        console.log('Resposta da API:', response.data);
        return response.data;
    }
}; 