package br.barberhub.backendApplication.controller;


import br.barberhub.backendApplication.model.Estabelecimento;
import br.barberhub.backendApplication.service.AdministradorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/sistema")
public class AdministradorController {

    @Autowired
    private AdministradorService sistemaService;
    

    @GetMapping("/estabelecimentos/pendentes")
    public ResponseEntity<List<Estabelecimento>> listarEstabelecimentosPendentes() {
        return ResponseEntity.ok(sistemaService.listarEstabelecimentosPendentes());
    }

	/*
	 * @GetMapping("/clientes/pendentes") public ResponseEntity<List<Cliente>>
	 * listarClientesPendentes() { return
	 * ResponseEntity.ok(sistemaService.listarClientesPendentes()); }
	 */

    
}