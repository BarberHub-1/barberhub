package br.barberhub.backendApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.barberhub.backendApplication.model.HorarioFuncionamento;

@Repository
public interface HorarioFuncionamentoRepository extends JpaRepository<HorarioFuncionamento, Long> {
    List<HorarioFuncionamento> findByEstabelecimentoId(Long estabelecimentoId);
} 