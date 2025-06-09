package br.barberhub.backendApplication.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.barberhub.backendApplication.dto.EstabelecimentoDTO;
import br.barberhub.backendApplication.model.Estabelecimento;
import br.barberhub.backendApplication.model.StatusCadastro;
import br.barberhub.backendApplication.repository.EstabelecimentoRepository;
import br.barberhub.backendApplication.service.EstabelecimentoService;

@RestController
@RequestMapping("/barbearias")
@CrossOrigin(origins = "*")
public class BarbeariaController {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @GetMapping
    public ResponseEntity<List<EstabelecimentoDTO>> listarBarbearias() {
        List<Estabelecimento> estabelecimentos = estabelecimentoRepository.findByStatus(StatusCadastro.APROVADO);
        List<EstabelecimentoDTO> estabelecimentosDTO = estabelecimentos.stream()
            .map(estabelecimentoService::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(estabelecimentosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstabelecimentoDTO> buscarBarbeariaPorId(@PathVariable Long id) {
        return estabelecimentoRepository.findById(id)
            .filter(estabelecimento -> estabelecimento.getStatus() == StatusCadastro.APROVADO)
            .map(estabelecimentoService::toDTO)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
} 