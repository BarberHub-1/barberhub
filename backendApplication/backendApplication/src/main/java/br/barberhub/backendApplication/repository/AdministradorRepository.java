package br.barberhub.backendApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.barberhub.backendApplication.model.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

}
