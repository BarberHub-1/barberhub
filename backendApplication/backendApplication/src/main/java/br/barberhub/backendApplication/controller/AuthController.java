package br.barberhub.backendApplication.controller;

import br.barberhub.backendApplication.dto.AuthResponseDTO;
import br.barberhub.backendApplication.dto.LoginDTO;
import br.barberhub.backendApplication.dto.RegisterDTO;
import br.barberhub.backendApplication.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // Endpoint temporário para recodificar a senha do administrador para depuração
    @PutMapping("/admin/recode-password/{email}/{password}")
    public ResponseEntity<String> recodeAdminPassword(@PathVariable String email, @PathVariable String password) {
        try {
            authService.recodeUserPassword(email, password);
            return ResponseEntity.ok("Senha do usuário " + email + " recodificada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao recodificar senha: " + e.getMessage());
        }
    }
}
