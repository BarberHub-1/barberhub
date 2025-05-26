package br.barberhub.backendApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.barberhub.backendApplication.model.AgendamentoServico;

@Repository
public interface AgendamentoServicoRepository extends JpaRepository<AgendamentoServico, Long> {
    List<AgendamentoServico> findByAgendamentoId(Long agendamentoId);
    List<AgendamentoServico> findByServicoId(Long servicoId);
} 
