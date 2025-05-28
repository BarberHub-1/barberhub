package br.barberhub.backendApplication.service;

import br.barberhub.backendApplication.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("Attempting to load user by email: {}", email);
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("User not found with email: {}", email);
                    return new UsernameNotFoundException("Usuário não encontrado com o email: " + email);
                });
    }
} 