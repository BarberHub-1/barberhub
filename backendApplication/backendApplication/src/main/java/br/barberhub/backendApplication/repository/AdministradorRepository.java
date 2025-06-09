package br.barberhub.backendApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.barberhub.backendApplication.model.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

	UserDetails findByEmail(String email);

}
