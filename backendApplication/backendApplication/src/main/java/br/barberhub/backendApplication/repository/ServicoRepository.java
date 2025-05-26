package br.barberhub.backendApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.barberhub.backendApplication.model.Servico;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
	List<Servico> findByEstabelecimentoId(Long estabelecimentoId);
}
