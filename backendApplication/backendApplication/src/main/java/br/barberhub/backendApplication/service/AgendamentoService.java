package br.barberhub.backendApplication.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.barberhub.backendApplication.dto.AgendamentoDTO;
import br.barberhub.backendApplication.dto.AvaliacaoDTO;
import br.barberhub.backendApplication.model.Agendamento;
import br.barberhub.backendApplication.model.AgendamentoServico;
import br.barberhub.backendApplication.model.Avaliacao;
import br.barberhub.backendApplication.model.Cliente;
import br.barberhub.backendApplication.model.Estabelecimento;
import br.barberhub.backendApplication.model.Servico;
import br.barberhub.backendApplication.model.StatusAgendamento;
import br.barberhub.backendApplication.repository.AgendamentoRepository;
import br.barberhub.backendApplication.repository.AgendamentoServicoRepository;
import br.barberhub.backendApplication.repository.ClienteRepository;
import br.barberhub.backendApplication.repository.EstabelecimentoRepository;
import br.barberhub.backendApplication.repository.ServicoRepository;


@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private AgendamentoServicoRepository agendamentoServicoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AgendamentoDTO criarAgendamento(AgendamentoDTO agendamentoDTO) {
        Agendamento agendamento = new Agendamento();
        
        Cliente cliente = clienteRepository.findById(agendamentoDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        agendamento.setCliente(cliente);
        
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(agendamentoDTO.getEstabelecimentoId())
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));
        agendamento.setEstabelecimento(estabelecimento);
        
        agendamento.setDataHora(agendamentoDTO.getDataHora());
        agendamento.setStatus(StatusAgendamento.AGENDADA);
        
        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);
        
        // Salvar os serviços do agendamento
        for (Long servicoId : agendamentoDTO.getServicos()) {
            Servico servico = servicoRepository.findById(servicoId)
                    .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
            
            AgendamentoServico agendamentoServico = new AgendamentoServico();
            agendamentoServico.setAgendamento(agendamentoSalvo);
            agendamentoServico.setServico(servico);
            agendamentoServicoRepository.save(agendamentoServico);
        }
        
        AgendamentoDTO response = new AgendamentoDTO();
        response.setId(agendamentoSalvo.getId());
        response.setClienteId(agendamentoSalvo.getCliente().getId());
        response.setEstabelecimentoId(agendamentoSalvo.getEstabelecimento().getId());
        response.setEstabelecimentoNome(agendamentoSalvo.getEstabelecimento().getNomeEstabelecimento());
        response.setDataHora(agendamentoSalvo.getDataHora());
        response.setStatusAgendamento(agendamentoSalvo.getStatus());
        response.setServicos(agendamentoDTO.getServicos());
        response.setServicosNomes(agendamentoSalvo.getServicos().stream()
                .map(as -> as.getServico().getTipo().toString())
                .collect(Collectors.toList()));
        
        return response;
    }

    public List<AgendamentoDTO> listarAgendamentosPorCliente(Long clienteId) {
        List<Agendamento> agendamentos = agendamentoRepository.findByClienteIdWithDetails(clienteId);
        return agendamentos.stream()
                .map(agendamento -> {
                    AgendamentoDTO dto = new AgendamentoDTO();
                    dto.setId(agendamento.getId());
                    dto.setClienteId(agendamento.getCliente().getId());
                    dto.setEstabelecimentoId(agendamento.getEstabelecimento().getId());
                    dto.setEstabelecimentoNome(agendamento.getEstabelecimento().getNomeEstabelecimento());
                    dto.setDataHora(agendamento.getDataHora());
                    dto.setStatusAgendamento(agendamento.getStatus());
                    dto.setServicos(agendamento.getServicos().stream()
                            .map(as -> as.getServico().getId())
                            .collect(Collectors.toList()));
                    dto.setServicosNomes(agendamento.getServicos().stream()
                            .map(as -> as.getServico().getTipo().toString())
                            .collect(Collectors.toList()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<AgendamentoDTO> listarAgendamentosPorEstabelecimento(Long estabelecimentoId) {
        List<Agendamento> agendamentos = agendamentoRepository.findByEstabelecimentoId(estabelecimentoId);
        return agendamentos.stream()
                .map(agendamento -> {
                    AgendamentoDTO dto = new AgendamentoDTO();
                    dto.setId(agendamento.getId());
                    dto.setClienteId(agendamento.getCliente().getId());
                    dto.setEstabelecimentoId(agendamento.getEstabelecimento().getId());
                    dto.setEstabelecimentoNome(agendamento.getEstabelecimento().getNomeEstabelecimento());
                    dto.setDataHora(agendamento.getDataHora());
                    dto.setStatusAgendamento(agendamento.getStatus());
                    dto.setServicos(agendamento.getServicos().stream()
                            .map(as -> as.getServico().getId())
                            .collect(Collectors.toList()));
                    dto.setServicosNomes(agendamento.getServicos().stream()
                            .map(as -> as.getServico().getTipo().toString())
                            .collect(Collectors.toList()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public AgendamentoDTO buscarAgendamentoPorId(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        
        AgendamentoDTO dto = new AgendamentoDTO();
        dto.setId(agendamento.getId());
        dto.setClienteId(agendamento.getCliente().getId());
        dto.setEstabelecimentoId(agendamento.getEstabelecimento().getId());
        dto.setEstabelecimentoNome(agendamento.getEstabelecimento().getNomeEstabelecimento());
        dto.setDataHora(agendamento.getDataHora());
        dto.setStatusAgendamento(agendamento.getStatus());
        dto.setServicos(agendamento.getServicos().stream()
                .map(as -> as.getServico().getId())
                .collect(Collectors.toList()));
        dto.setServicosNomes(agendamento.getServicos().stream()
                .map(as -> as.getServico().getTipo().toString())
                .collect(Collectors.toList()));
        
        return dto;
    }

    public AgendamentoDTO atualizarStatus(Long id, StatusAgendamento status) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        
        agendamento.setStatus(status);
        Agendamento agendamentoAtualizado = agendamentoRepository.save(agendamento);
        
        AgendamentoDTO dto = new AgendamentoDTO();
        dto.setId(agendamentoAtualizado.getId());
        dto.setClienteId(agendamentoAtualizado.getCliente().getId());
        dto.setEstabelecimentoId(agendamentoAtualizado.getEstabelecimento().getId());
        dto.setEstabelecimentoNome(agendamentoAtualizado.getEstabelecimento().getNomeEstabelecimento());
        dto.setDataHora(agendamentoAtualizado.getDataHora());
        dto.setStatusAgendamento(agendamentoAtualizado.getStatus());
        dto.setServicos(agendamentoAtualizado.getServicos().stream()
                .map(as -> as.getServico().getId())
                .collect(Collectors.toList()));
        dto.setServicosNomes(agendamentoAtualizado.getServicos().stream()
                .map(as -> as.getServico().getTipo().toString())
                .collect(Collectors.toList()));
        
        return dto;
    }

    public AgendamentoDTO avaliarAgendamento(Long id, AvaliacaoDTO avaliacaoDTO) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        
        if (agendamento.getStatus() != StatusAgendamento.CONCLUIDA) {
            throw new RuntimeException("Apenas agendamentos concluídos podem ser avaliados");
        }
        
        Avaliacao avaliacao = modelMapper.map(avaliacaoDTO, Avaliacao.class);
        avaliacao.setAgendamento(agendamento);
        avaliacao.setEstabelecimento(agendamento.getEstabelecimento());
        
        agendamento.setAvaliacao(avaliacao);
        Agendamento agendamentoAtualizado = agendamentoRepository.save(agendamento);
        return modelMapper.map(agendamentoAtualizado, AgendamentoDTO.class);
    }

    public void excluirAgendamento(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        agendamentoRepository.delete(agendamento);
    }
} 