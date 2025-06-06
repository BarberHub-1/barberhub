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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.barberhub.backendApplication.dto.AgendamentoDTO;
import br.barberhub.backendApplication.dto.AvaliacaoDTO;
import br.barberhub.backendApplication.model.StatusAgendamento;
import br.barberhub.backendApplication.service.AgendamentoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoDTO> criarAgendamento(@Valid @RequestBody AgendamentoDTO agendamentoDTO) {
        return ResponseEntity.ok(agendamentoService.criarAgendamento(agendamentoDTO));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<AgendamentoDTO>> listarAgendamentosPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(agendamentoService.listarAgendamentosPorCliente(clienteId));
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    public ResponseEntity<List<AgendamentoDTO>> listarAgendamentosPorEstabelecimento(@PathVariable Long estabelecimentoId) {
        return ResponseEntity.ok(agendamentoService.listarAgendamentosPorEstabelecimento(estabelecimentoId));
    }


    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> buscarAgendamentoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.buscarAgendamentoPorId(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<AgendamentoDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestParam @Pattern(regexp = "AGENDADA|CANCELADA|CONCLUIDA", message = "Status inválido") String status) {
        return ResponseEntity.ok(agendamentoService.atualizarStatus(id, StatusAgendamento.valueOf(status)));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<AgendamentoDTO> cancelarAgendamento(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.atualizarStatus(id, StatusAgendamento.CANCELADA));
    }

    @PutMapping("/{id}/avaliacao")
    public ResponseEntity<AgendamentoDTO> avaliarAgendamento(
            @PathVariable Long id,
            @Valid @RequestBody AvaliacaoDTO avaliacaoDTO) {
        return ResponseEntity.ok(agendamentoService.avaliarAgendamento(id, avaliacaoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAgendamento(@PathVariable Long id) {
        agendamentoService.excluirAgendamento(id);
        return ResponseEntity.ok().build();
    }
} 