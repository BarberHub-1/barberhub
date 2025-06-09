package br.barberhub.backendApplication.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

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
import br.barberhub.backendApplication.repository.AvaliacaoRepository;


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

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    public AgendamentoDTO criarAgendamento(AgendamentoDTO agendamentoDTO) {
        System.out.println("Iniciando criação de agendamento com dados: " + agendamentoDTO);
        System.out.println("Data e hora recebidas: " + agendamentoDTO.getDataHora());
        
        Agendamento agendamento = new Agendamento();
        
        Cliente cliente = clienteRepository.findById(agendamentoDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        agendamento.setCliente(cliente);
        
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(agendamentoDTO.getEstabelecimentoId())
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));
        agendamento.setEstabelecimento(estabelecimento);
        
        // Garantir que a data e hora sejam mantidas como recebidas
        agendamento.setDataHora(agendamentoDTO.getDataHora());
        System.out.println("Data e hora definidas no agendamento: " + agendamento.getDataHora());
        
        agendamento.setStatus(StatusAgendamento.AGENDADA);
        
        System.out.println("Salvando agendamento base...");
        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);
        System.out.println("Agendamento base salvo com ID: " + agendamentoSalvo.getId());
        
        List<AgendamentoServico> agendamentoServicos = new ArrayList<>();
        
        // Salvar os serviços do agendamento
        for (Long servicoId : agendamentoDTO.getServicos()) {
            System.out.println("Processando serviço ID: " + servicoId);
            Servico servico = servicoRepository.findById(servicoId)
                    .orElseThrow(() -> new RuntimeException("Serviço não encontrado: " + servicoId));
            
            AgendamentoServico agendamentoServico = new AgendamentoServico();
            agendamentoServico.setAgendamento(agendamentoSalvo);
            agendamentoServico.setServico(servico);
            agendamentoServicos.add(agendamentoServico);
        }
        
        System.out.println("Salvando " + agendamentoServicos.size() + " serviços do agendamento...");
        agendamentoServicoRepository.saveAll(agendamentoServicos);
        
        // Buscar o agendamento atualizado com todos os relacionamentos
        Agendamento agendamentoCompleto = agendamentoRepository.findById(agendamentoSalvo.getId())
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado após salvar serviços"));
        
        AgendamentoDTO response = new AgendamentoDTO();
        response.setId(agendamentoCompleto.getId());
        response.setClienteId(agendamentoCompleto.getCliente().getId());
        response.setEstabelecimentoId(agendamentoCompleto.getEstabelecimento().getId());
        response.setEstabelecimentoNome(agendamentoCompleto.getEstabelecimento().getNomeEstabelecimento());
        response.setDataHora(agendamentoCompleto.getDataHora());
        response.setStatusAgendamento(agendamentoCompleto.getStatus());
        response.setServicos(agendamentoDTO.getServicos());
        
        // Buscar os serviços salvos para obter os nomes
        List<AgendamentoServico> servicosSalvos = agendamentoServicoRepository.findByAgendamentoId(agendamentoCompleto.getId());
        response.setServicosNomes(servicosSalvos.stream()
                .map(as -> as.getServico().getTipo().toString())
                .collect(Collectors.toList()));
        
        System.out.println("Agendamento criado com sucesso: " + response);
        return response;
    }

    public List<AgendamentoDTO> listarAgendamentosPorCliente(Long clienteId) {
        System.out.println("Buscando agendamentos do cliente: " + clienteId);
        List<Agendamento> agendamentos = agendamentoRepository.findByClienteIdWithDetails(clienteId);
        System.out.println("Agendamentos encontrados: " + agendamentos.size());
        
        return agendamentos.stream()
                .map(agendamento -> {
                    System.out.println("Processando agendamento: " + agendamento.getId());
                    System.out.println("Avaliação do agendamento: " + agendamento.getAvaliacao());
                    
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
                    
                    if (agendamento.getAvaliacao() != null) {
                        System.out.println("Adicionando avaliação ao DTO");
                        dto.setAvaliacao(modelMapper.map(agendamento.getAvaliacao(), AvaliacaoDTO.class));
                    }
                    
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
        agendamentoRepository.save(agendamento);
        
        // Buscar o agendamento atualizado com todos os relacionamentos
        Agendamento agendamentoAtualizado = agendamentoRepository.findByClienteIdWithDetails(agendamento.getCliente().getId())
                .stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado após atualização"));
        
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
        try {
            System.out.println("Iniciando avaliação do agendamento: " + id);
            System.out.println("Payload recebido: " + avaliacaoDTO);
            
            Agendamento agendamento = agendamentoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Agendamento nao encontrado"));
            
            System.out.println("Agendamento encontrado: " + agendamento);
            
            // Buscar o agendamento com todos os relacionamentos
            agendamento = agendamentoRepository.findByClienteIdWithDetails(agendamento.getCliente().getId())
                    .stream()
                    .filter(a -> a.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Agendamento nao encontrado"));
            
            System.out.println("Status do agendamento: " + agendamento.getStatus());
            
            if (agendamento.getStatus() != StatusAgendamento.CONCLUIDA) {
                throw new RuntimeException("Apenas agendamentos concluidos podem ser avaliados");
            }

            if (agendamento.getAvaliacao() != null) {
                throw new RuntimeException("Este agendamento ja foi avaliado");
            }
            
            Avaliacao avaliacao = new Avaliacao();
            avaliacao.setNota(avaliacaoDTO.getNota());
            avaliacao.setComentario(avaliacaoDTO.getComentario());
            avaliacao.setDataAvaliacao(avaliacaoDTO.getDateTime());
            avaliacao.setAgendamento(agendamento);
            avaliacao.setEstabelecimento(agendamento.getEstabelecimento());
            
            System.out.println("Salvando avaliação: " + avaliacao);
            
            try {
                avaliacao = avaliacaoRepository.save(avaliacao);
                System.out.println("Avaliação salva com sucesso: " + avaliacao);
                
                agendamento.setAvaliacao(avaliacao);
                Agendamento agendamentoAtualizado = agendamentoRepository.save(agendamento);
                System.out.println("Agendamento atualizado com sucesso: " + agendamentoAtualizado);
                
                // Mapeamento manual para evitar problemas com o ModelMapper
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
            } catch (Exception e) {
                System.err.println("Erro ao salvar avaliação: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Erro ao salvar avaliação: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Erro ao processar avaliação: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao processar avaliação: " + e.getMessage());
        }
    }

    public void excluirAgendamento(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        agendamentoRepository.delete(agendamento);
    }
} 