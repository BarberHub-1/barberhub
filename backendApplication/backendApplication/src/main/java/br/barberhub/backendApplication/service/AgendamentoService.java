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
        
        Cliente cliente = clienteRepository.findById(agendamentoDTO.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        agendamento.setCliente(cliente);
        
        
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(agendamentoDTO.getEstabelecimento().getId())
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
        
        return modelMapper.map(agendamentoSalvo, AgendamentoDTO.class);
    }

    public List<AgendamentoDTO> listarAgendamentosPorCliente(Long clienteId) {
        List<Agendamento> agendamentos = agendamentoRepository.findByClienteId(clienteId);
        return agendamentos.stream()
                .map(agendamento -> modelMapper.map(agendamento, AgendamentoDTO.class))
                .collect(Collectors.toList());
    }

    public List<AgendamentoDTO> listarAgendamentosPorEstabelecimento(Long estabelecimentoId) {
        List<Agendamento> agendamentos = agendamentoRepository.findByEstabelecimentoId(estabelecimentoId);
        return agendamentos.stream()
                .map(agendamento -> modelMapper.map(agendamento, AgendamentoDTO.class))
                .collect(Collectors.toList());
    }

   
    public AgendamentoDTO buscarAgendamentoPorId(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        return modelMapper.map(agendamento, AgendamentoDTO.class);
    }

    public AgendamentoDTO atualizarStatus(Long id, StatusAgendamento status) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        
        agendamento.setStatus(status);
        Agendamento agendamentoAtualizado = agendamentoRepository.save(agendamento);
        return modelMapper.map(agendamentoAtualizado, AgendamentoDTO.class);
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