package br.barberhub.backendApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.barberhub.backendApplication.model.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByEstabelecimentoId(Long estabelecimentoId);
	List<Agendamento> findByClienteId(Long clienteId);
}
