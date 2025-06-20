package br.barberhub.backendApplication.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import br.barberhub.backendApplication.dto.EstabelecimentoDTO;
import br.barberhub.backendApplication.service.EstabelecimentoService;
import jakarta.validation.Valid;
import br.barberhub.backendApplication.model.Estabelecimento;
import br.barberhub.backendApplication.repository.EstabelecimentoRepository;
import br.barberhub.backendApplication.model.StatusCadastro;
import br.barberhub.backendApplication.model.Servico;
import br.barberhub.backendApplication.repository.ServicoRepository;
import br.barberhub.backendApplication.model.HorarioFuncionamento;
import br.barberhub.backendApplication.repository.HorarioFuncionamentoRepository;
import lombok.Data;

@RestController
@RequestMapping("/api/estabelecimentos")
public class EstabelecimentoController {

    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private HorarioFuncionamentoRepository horarioFuncionamentoRepository;

    @Data
    public static class StatusUpdateRequest {
        private String status;
    }

    @PostMapping
    public ResponseEntity<EstabelecimentoDTO> cadastrarEstabelecimento(@Valid @RequestBody EstabelecimentoDTO estabelecimentoDTO) {
        return ResponseEntity.ok(estabelecimentoService.cadastrarEstabelecimento(estabelecimentoDTO));
    }

    @GetMapping
    public ResponseEntity<List<EstabelecimentoDTO>> listarEstabelecimentos() {
        return ResponseEntity.ok(estabelecimentoService.listarEstabelecimentos());
    }

    @GetMapping("/all")
    public ResponseEntity<List<EstabelecimentoDTO>> listarTodosEstabelecimentos() {
        return ResponseEntity.ok(estabelecimentoService.listarTodosEstabelecimentos());
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

    @PatchMapping("/{id}/status")
    public ResponseEntity<EstabelecimentoDTO> alterarStatusEstabelecimento(
            @PathVariable Long id,
            @RequestBody StatusUpdateRequest request) {
        System.out.println("Recebida requisição PATCH para alterar status do estabelecimento ID: " + id);
        System.out.println("Novo status: " + request.getStatus());
        try {
            EstabelecimentoDTO resultado = estabelecimentoService.alterarStatusEstabelecimento(id, request.getStatus());
            System.out.println("Status alterado com sucesso para: " + resultado.getStatus());
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            System.err.println("Erro ao alterar status: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<EstabelecimentoDTO> alterarStatusEstabelecimentoPut(
            @PathVariable Long id,
            @RequestBody StatusUpdateRequest request) {
        System.out.println("Recebida requisição PUT para alterar status do estabelecimento ID: " + id);
        System.out.println("Novo status: " + request.getStatus());
        try {
            EstabelecimentoDTO resultado = estabelecimentoService.alterarStatusEstabelecimento(id, request.getStatus());
            System.out.println("Status alterado com sucesso para: " + resultado.getStatus());
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            System.err.println("Erro ao alterar status: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @PostMapping("/teste")
    public ResponseEntity<String> testeCadastro() {
        try {
            Estabelecimento estabelecimento = new Estabelecimento();
            estabelecimento.setEmail("teste@teste.com");
            estabelecimento.setSenha("123456");
            estabelecimento.setNomeProprietario("Teste");
            estabelecimento.setNomeEstabelecimento("Barbearia Teste");
            estabelecimento.setCnpj("12.345.678/0001-90");
            estabelecimento.setTelefone("(11) 99999-9999");
            estabelecimento.setStatus(StatusCadastro.PENDENTE);
            
            Estabelecimento salvo = estabelecimentoRepository.save(estabelecimento);
            
            return ResponseEntity.ok("Estabelecimento salvo com ID: " + salvo.getId());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro: " + e.getMessage());
        }
    }

    @GetMapping("/teste-status/{id}")
    public ResponseEntity<String> testeStatus(@PathVariable Long id) {
        try {
            Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));
            
            return ResponseEntity.ok("Estabelecimento ID: " + id + 
                                   ", Nome: " + estabelecimento.getNomeEstabelecimento() + 
                                   ", Status atual: " + estabelecimento.getStatus());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/servicos")
    public ResponseEntity<List<Servico>> getServicosDoEstabelecimento(@PathVariable Long id) {
        try {
            List<Servico> servicos = servicoRepository.findByEstabelecimentoId(id);
            return ResponseEntity.ok(servicos);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ArrayList<>());
        }
    }

    @GetMapping("/{id}/horarios")
    public ResponseEntity<List<HorarioFuncionamento>> getHorariosDoEstabelecimento(@PathVariable Long id) {
        try {
            List<HorarioFuncionamento> horarios = horarioFuncionamentoRepository.findByEstabelecimentoId(id);
            return ResponseEntity.ok(horarios);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ArrayList<>());
        }
    }
} 
