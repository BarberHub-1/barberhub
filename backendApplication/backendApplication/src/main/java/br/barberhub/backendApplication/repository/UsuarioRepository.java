package br.barberhub.backendApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.barberhub.backendApplication.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
}