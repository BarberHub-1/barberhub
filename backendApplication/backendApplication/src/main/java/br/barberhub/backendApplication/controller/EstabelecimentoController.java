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

import br.barberhub.backendApplication.dto.EstabelecimentoDTO;
import br.barberhub.backendApplication.service.EstabelecimentoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/estabelecimentos")
public class EstabelecimentoController {

    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @PostMapping
    public ResponseEntity<EstabelecimentoDTO> cadastrarEstabelecimento(@Valid @RequestBody EstabelecimentoDTO estabelecimentoDTO) {
        return ResponseEntity.ok(estabelecimentoService.cadastrarEstabelecimento(estabelecimentoDTO));
    }

    @GetMapping
    public ResponseEntity<List<EstabelecimentoDTO>> listarEstabelecimentos() {
        return ResponseEntity.ok(estabelecimentoService.listarEstabelecimentos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstabelecimentoDTO> buscarEstabelecimentoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(estabelecimentoService.buscarEstabelecimentoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstabelecimentoDTO> atualizarEstabelecimento(
            @PathVariable Long id,
            @Valid @RequestBody EstabelecimentoDTO estabelecimentoDTO) {
        return ResponseEntity.ok(estabelecimentoService.atualizarEstabelecimento(id, estabelecimentoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEstabelecimento(@PathVariable Long id) {
        estabelecimentoService.excluirEstabelecimento(id);
        return ResponseEntity.ok().build();
    }
} 
