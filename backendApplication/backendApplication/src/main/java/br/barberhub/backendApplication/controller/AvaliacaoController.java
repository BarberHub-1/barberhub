package br.barberhub.backendApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.barberhub.backendApplication.dto.AvaliacaoDTO;
import br.barberhub.backendApplication.service.AvaliacaoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @PostMapping
    public ResponseEntity<AvaliacaoDTO> cadastrarAvaliacao(@Valid @RequestBody AvaliacaoDTO avaliacaoDTO) {
        return ResponseEntity.ok(avaliacaoService.cadastrarAvaliacao(avaliacaoDTO));
    }

    @GetMapping
    public ResponseEntity<List<AvaliacaoDTO>> listarAvaliacoes() {
        return ResponseEntity.ok(avaliacaoService.listarAvaliacoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoDTO> buscarAvaliacaoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(avaliacaoService.buscarAvaliacaoPorId(id));
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    public ResponseEntity<List<AvaliacaoDTO>> buscarAvaliacoesPorEstabelecimento(@PathVariable Long estabelecimentoId) {
        return ResponseEntity.ok(avaliacaoService.buscarAvaliacoesPorEstabelecimento(estabelecimentoId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAvaliacao(@PathVariable Long id) {
        avaliacaoService.excluirAvaliacao(id);
        return ResponseEntity.ok().build();
    }
} 