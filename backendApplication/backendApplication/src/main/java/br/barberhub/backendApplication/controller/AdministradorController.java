package br.barberhub.backendApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.barberhub.backendApplication.dto.EstabelecimentoDTO;
import br.barberhub.backendApplication.service.AdministradorService;

@RestController
@RequestMapping("/api/admin")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @GetMapping("/estabelecimentos/pendentes")
    public ResponseEntity<List<EstabelecimentoDTO>> listarEstabelecimentosPendentes() {
        return ResponseEntity.ok(administradorService.listarEstabelecimentosPendentes());
    }

    @PostMapping("/estabelecimentos/{id}/aprovar")
    public ResponseEntity<EstabelecimentoDTO> aprovarEstabelecimento(@PathVariable Long id) {
        return ResponseEntity.ok(administradorService.aprovarEstabelecimento(id));
    }

    @PostMapping("/estabelecimentos/{id}/rejeitar")
    public ResponseEntity<EstabelecimentoDTO> rejeitarEstabelecimento(@PathVariable Long id) {
        return ResponseEntity.ok(administradorService.rejeitarEstabelecimento(id));
    }

	/*
	 * @GetMapping("/clientes/pendentes") public ResponseEntity<List<Cliente>>
	 * listarClientesPendentes() { return
	 * ResponseEntity.ok(sistemaService.listarClientesPendentes()); }
	 */

    
}