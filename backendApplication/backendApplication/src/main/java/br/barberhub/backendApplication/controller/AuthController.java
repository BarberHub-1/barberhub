package br.barberhub.backendApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.barberhub.backendApplication.dto.AuthDTO;
import br.barberhub.backendApplication.model.Usuario;
import br.barberhub.backendApplication.repository.UsuarioRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;


     
   
}
