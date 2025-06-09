package br.barberhub.backendApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.barberhub.backendApplication.dto.ServicoDTO;
import br.barberhub.backendApplication.service.ServicoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @PostMapping
    public ResponseEntity<ServicoDTO> cadastrarServico(@Valid @RequestBody ServicoDTO servicoDTO) {
        return ResponseEntity.ok(servicoService.cadastrarServico(servicoDTO));
    }

    @GetMapping("/barbearia/{barbeariaId}")
    public ResponseEntity<List<ServicoDTO>> listarServicosPorBarbearia(@PathVariable Long barbeariaId) {
        return ResponseEntity.ok(servicoService.listarServicosPorEstabelecimento(barbeariaId));
    }

    @GetMapping("/profissional/{profissionalId}")
    public ResponseEntity<List<ServicoDTO>> listarServicosPorProfissional(@PathVariable Long profissionalId) {
        return ResponseEntity.ok(servicoService.listarServicosPorProfissional(profissionalId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoDTO> buscarServicoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicoService.buscarServicoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoDTO> atualizarServico(
            @PathVariable Long id,
            @Valid @RequestBody ServicoDTO servicoDTO) {
        return ResponseEntity.ok(servicoService.atualizarServico(id, servicoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirServico(@PathVariable Long id) {
        servicoService.excluirServico(id);
        return ResponseEntity.ok().build();
    }
} 
