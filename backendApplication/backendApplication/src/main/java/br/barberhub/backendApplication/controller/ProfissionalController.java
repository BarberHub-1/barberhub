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

import br.barberhub.backendApplication.dto.ProfissionalDTO;
import br.barberhub.backendApplication.service.ProfissionalService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/profissionais")
public class ProfissionalController {

    @Autowired
    private ProfissionalService profissionalService;

    @PostMapping
    public ResponseEntity<ProfissionalDTO> cadastrarProfissional(@Valid @RequestBody ProfissionalDTO profissionalDTO) {
        return ResponseEntity.ok(profissionalService.cadastrarProfissional(profissionalDTO));
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    public ResponseEntity<List<ProfissionalDTO>> listarProfissionaisPorEstabelecimento(@PathVariable Long estabelecimentoId) {
        return ResponseEntity.ok(profissionalService.listarProfissionaisPorEstabelecimento(estabelecimentoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalDTO> buscarProfissionalPorId(@PathVariable Long id) {
        return ResponseEntity.ok(profissionalService.buscarProfissionalPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalDTO> atualizarProfissional(
            @PathVariable Long id,
            @Valid @RequestBody ProfissionalDTO profissionalDTO) {
        return ResponseEntity.ok(profissionalService.atualizarProfissional(id, profissionalDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProfissional(@PathVariable Long id) {
        profissionalService.excluirProfissional(id);
        return ResponseEntity.ok().build();
    }
} 
