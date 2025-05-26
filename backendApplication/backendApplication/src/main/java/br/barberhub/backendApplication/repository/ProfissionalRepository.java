package br.barberhub.backendApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.barberhub.backendApplication.model.Profissional;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {
	List<Profissional> findByEstabelecimentoId(Long estabelecimentoId);
}
