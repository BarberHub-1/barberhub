package br.barberhub.backendApplication.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.barberhub.backendApplication.dto.AvaliacaoDTO;
import br.barberhub.backendApplication.model.Agendamento;
import br.barberhub.backendApplication.model.Avaliacao;
import br.barberhub.backendApplication.model.Estabelecimento;
import br.barberhub.backendApplication.repository.AgendamentoRepository;
import br.barberhub.backendApplication.repository.AvaliacaoRepository;
import br.barberhub.backendApplication.repository.EstabelecimentoRepository;



@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;


    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AvaliacaoDTO cadastrarAvaliacao(AvaliacaoDTO avaliacaoDTO) {
        Avaliacao avaliacao = modelMapper.map(avaliacaoDTO, Avaliacao.class);
        
        Agendamento agendamento = agendamentoRepository.findById(avaliacaoDTO.getAgendamentoId())
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        avaliacao.setAgendamento(agendamento);


        Estabelecimento estabelecimento = estabelecimentoRepository.findById(avaliacaoDTO.getAgendamentoId())
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));
        avaliacao.setEstabelecimento(estabelecimento);

        Avaliacao avaliacaoSalva = avaliacaoRepository.save(avaliacao);
        return modelMapper.map(avaliacaoSalva, AvaliacaoDTO.class);
    }

    public List<AvaliacaoDTO> listarAvaliacoes() {
        List<Avaliacao> avaliacoes = avaliacaoRepository.findAll();
        return avaliacoes.stream()
                .map(avaliacao -> modelMapper.map(avaliacao, AvaliacaoDTO.class))
                .collect(Collectors.toList());
    }

    public AvaliacaoDTO buscarAvaliacaoPorId(Long id) {
        Avaliacao avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));
        return modelMapper.map(avaliacao, AvaliacaoDTO.class);
    }

    public List<AvaliacaoDTO> buscarAvaliacoesPorEstabelecimento(Long estabelecimentoId) {
        List<Avaliacao> avaliacoes = avaliacaoRepository.findByEstabelecimentoId(estabelecimentoId);
        return avaliacoes.stream()
                .map(avaliacao -> modelMapper.map(avaliacao, AvaliacaoDTO.class))
                .collect(Collectors.toList());
    }

    public void excluirAvaliacao(Long id) {
        Avaliacao avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));
        avaliacaoRepository.delete(avaliacao);
    }
} 