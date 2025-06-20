package br.barberhub.backendApplication.service;

import br.barberhub.backendApplication.model.StatusCadastro;
import br.barberhub.backendApplication.repository.AgendamentoRepository;
import br.barberhub.backendApplication.repository.ClienteRepository;
import br.barberhub.backendApplication.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalUsuarios", clienteRepository.count());
        stats.put("totalEstabelecimentos", estabelecimentoRepository.count());
        stats.put("totalAgendamentos", agendamentoRepository.count());
        stats.put("estabelecimentosPendentes", estabelecimentoRepository.countByStatus(StatusCadastro.PENDENTE));
        return stats;
    }
} 