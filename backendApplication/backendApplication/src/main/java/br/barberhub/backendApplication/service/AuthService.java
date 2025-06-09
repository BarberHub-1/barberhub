package br.barberhub.backendApplication.service;

import br.barberhub.backendApplication.dto.AuthResponseDTO;
import br.barberhub.backendApplication.dto.LoginDTO;
import br.barberhub.backendApplication.dto.RegisterDTO;
import br.barberhub.backendApplication.model.Cliente;
import br.barberhub.backendApplication.model.Estabelecimento;
import br.barberhub.backendApplication.model.Administrador;
import br.barberhub.backendApplication.model.Usuario;
import br.barberhub.backendApplication.repository.ClienteRepository;
import br.barberhub.backendApplication.repository.EstabelecimentoRepository;
import br.barberhub.backendApplication.repository.AdministradorRepository;
import br.barberhub.backendApplication.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.barberhub.backendApplication.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final ClienteRepository clienteRepository;
    private final EstabelecimentoRepository estabelecimentoRepository;
    private final AdministradorRepository administradorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final UsuarioRepository usuarioRepository;

    public AuthResponseDTO register(RegisterDTO request) {
        Usuario usuario;
        String role;

        switch (request.getTipoUsuario()) {
            case CLIENTE:
                Cliente cliente = new Cliente();
                cliente.setEmail(request.getEmail());
                cliente.setSenha(passwordEncoder.encode(request.getSenha()));
                cliente.setNome(request.getNome());
                cliente.setTelefone(request.getTelefone());
                usuario = clienteRepository.save(cliente);
                break;

            case ESTABELECIMENTO:
                Estabelecimento estabelecimento = new Estabelecimento();
                estabelecimento.setEmail(request.getEmail());
                estabelecimento.setSenha(passwordEncoder.encode(request.getSenha()));
                estabelecimento.setNomeEstabelecimento(request.getNome());
                estabelecimento.setNomeProprietario(request.getNome());
                estabelecimento.setTelefone(request.getTelefone());
                usuario = estabelecimentoRepository.save(estabelecimento);
                break;

            case ADMIN:
                Administrador admin = new Administrador();
                admin.setEmail(request.getEmail());
                admin.setSenha(passwordEncoder.encode(request.getSenha()));
                usuario = administradorRepository.save(admin);
                break;

            default:
                throw new IllegalArgumentException("Tipo de usuário inválido");
        }

        String token = jwtService.generateToken(usuario);
        String tipo = usuario.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
        return new AuthResponseDTO(token, tipo, usuario.getId());
    }

    public AuthResponseDTO login(LoginDTO request) {
        logger.info("Attempting authentication for user: {}", request.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getSenha()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        logger.info("User details loaded for {}: Authorities - {}", userDetails.getUsername(), userDetails.getAuthorities());

        String token = jwtService.generateToken(userDetails);

        // Extrair o tipo e ID do usuário
        String tipo = userDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
        Long id = ((Usuario) userDetails).getId();

        logger.info("Authentication successful for {}. Returning token, type: {}, id: {}", userDetails.getUsername(), tipo, id);

        return new AuthResponseDTO(token, tipo, id);
    }

    // Método temporário para recodificar a senha de um usuário para depuração
    public void recodeUserPassword(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                                         .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
        usuario.setSenha(passwordEncoder.encode(password));
        usuarioRepository.save(usuario);
    }
} 