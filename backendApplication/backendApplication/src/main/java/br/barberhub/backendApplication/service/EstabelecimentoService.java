package br.barberhub.backendApplication.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.barberhub.backendApplication.dto.EstabelecimentoDTO;
import br.barberhub.backendApplication.model.Estabelecimento;
import br.barberhub.backendApplication.repository.EstabelecimentoRepository;

@Service
public class EstabelecimentoService {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public EstabelecimentoDTO cadastrarEstabelecimento(EstabelecimentoDTO estabelecimentoDTO) {
        Estabelecimento estabelecimento = modelMapper.map(estabelecimentoDTO, Estabelecimento.class);
        Estabelecimento estabelecimentoSalvo = estabelecimentoRepository.save(estabelecimento);
        return modelMapper.map(estabelecimentoSalvo, EstabelecimentoDTO.class);
    }

    public List<EstabelecimentoDTO> listarEstabelecimentos() {
        List<Estabelecimento> estabelecimentos = estabelecimentoRepository.findAll();
        return estabelecimentos.stream()
                .map(estabelecimento -> modelMapper.map(estabelecimento, EstabelecimentoDTO.class))
                .collect(Collectors.toList());
    }

    public EstabelecimentoDTO buscarEstabelecimentoPorId(Long id) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));
        return modelMapper.map(estabelecimento, EstabelecimentoDTO.class);
    }

    public EstabelecimentoDTO atualizarEstabelecimento(Long id, EstabelecimentoDTO estabelecimentoDTO) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));
        
        modelMapper.map(estabelecimentoDTO, estabelecimento);
        
        Estabelecimento estabelecimentoAtualizado = estabelecimentoRepository.save(estabelecimento);
        return modelMapper.map(estabelecimentoAtualizado, EstabelecimentoDTO.class);
    }

    public void excluirEstabelecimento(Long id) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));
        estabelecimentoRepository.delete(estabelecimento);
    }
} 