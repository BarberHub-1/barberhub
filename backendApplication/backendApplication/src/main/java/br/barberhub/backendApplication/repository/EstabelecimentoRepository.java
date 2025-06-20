package br.barberhub.backendApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.barberhub.backendApplication.model.Estabelecimento;
import br.barberhub.backendApplication.model.StatusCadastro;

@Repository
public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Long> {

	List<Estabelecimento> findByStatus(StatusCadastro status);
	long countByStatus(StatusCadastro status);

	UserDetails findByEmail(String email);

}
