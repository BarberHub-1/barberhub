package br.barberhub.backendApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EstabelecimentoDTO> atualizarEstabelecimento(
            @PathVariable Long id,
            @RequestPart("estabelecimento") @Valid EstabelecimentoDTO estabelecimentoDTO,
            @RequestPart(value = "foto", required = false) MultipartFile foto) {
        return ResponseEntity.ok(estabelecimentoService.atualizarEstabelecimento(id, estabelecimentoDTO, foto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEstabelecimento(@PathVariable Long id) {
        estabelecimentoService.excluirEstabelecimento(id);
        return ResponseEntity.ok().build();
    }
} 
