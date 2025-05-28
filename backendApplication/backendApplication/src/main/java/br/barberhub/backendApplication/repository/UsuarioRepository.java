package br.barberhub.backendApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.barberhub.backendApplication.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}