package br.barberhub.backendApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.barberhub.backendApplication.model.Avaliacao;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    List<Avaliacao> findByEstabelecimentoId(Long estabelecimentoId);
}
