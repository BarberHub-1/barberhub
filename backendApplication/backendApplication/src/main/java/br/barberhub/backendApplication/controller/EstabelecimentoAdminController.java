package br.barberhub.backendApplication.controller;

import br.barberhub.backendApplication.dto.EstabelecimentoDTO;
import br.barberhub.backendApplication.dto.EstabelecimentoStatusDTO;
import br.barberhub.backendApplication.model.StatusCadastro;
import br.barberhub.backendApplication.service.EstabelecimentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/estabelecimentos")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMINISTRADOR')") // Garante que apenas administradores acessem este controlador
public class EstabelecimentoAdminController {

    private final EstabelecimentoService estabelecimentoService;

    @GetMapping
    public ResponseEntity<List<EstabelecimentoDTO>> listarTodosEstabelecimentos() {
        List<EstabelecimentoDTO> estabelecimentos = estabelecimentoService.listarEstabelecimentos();
        return ResponseEntity.ok(estabelecimentos);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<EstabelecimentoDTO> atualizarStatusCadastro(
            @PathVariable Long id,
            @Valid @RequestBody EstabelecimentoStatusDTO statusDTO) {
        EstabelecimentoDTO estabelecimentoAtualizado = estabelecimentoService.atualizarStatusCadastro(id, statusDTO.getStatusCadastro());
        return ResponseEntity.ok(estabelecimentoAtualizado);
    }
} 