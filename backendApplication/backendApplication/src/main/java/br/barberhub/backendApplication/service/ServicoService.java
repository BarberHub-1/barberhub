package br.barberhub.backendApplication.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.barberhub.backendApplication.dto.ServicoDTO;
import br.barberhub.backendApplication.model.Estabelecimento;
import br.barberhub.backendApplication.model.Profissional;
import br.barberhub.backendApplication.model.Servico;
import br.barberhub.backendApplication.repository.EstabelecimentoRepository;
import br.barberhub.backendApplication.repository.ProfissionalRepository;
import br.barberhub.backendApplication.repository.ServicoRepository;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ServicoDTO cadastrarServico(ServicoDTO servicoDTO) {
        Servico servico = modelMapper.map(servicoDTO, Servico.class);
        
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(servicoDTO.getEstabelecimentoId())
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));
        servico.setEstabelecimento(estabelecimento);

        Servico servicoSalvo = servicoRepository.save(servico);
        return modelMapper.map(servicoSalvo, ServicoDTO.class);
    }

    public List<ServicoDTO> listarServicosPorEstabelecimento(Long estabelecimentoId) {
        List<Servico> servicos = servicoRepository.findByEstabelecimentoId(estabelecimentoId);
        return servicos.stream()
                .map(servico -> modelMapper.map(servico, ServicoDTO.class))
                .collect(Collectors.toList());
    }

    public List<ServicoDTO> listarServicosPorProfissional(Long profissionalId) {
        Profissional profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
        
        return profissional.getServicos().stream()
                .map(servico -> modelMapper.map(servico, ServicoDTO.class))
                .collect(Collectors.toList());
    }

    public ServicoDTO buscarServicoPorId(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        return modelMapper.map(servico, ServicoDTO.class);
    }

    public ServicoDTO atualizarServico(Long id, ServicoDTO servicoDTO) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        
        modelMapper.map(servicoDTO, servico);

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(servicoDTO.getEstabelecimentoId())
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));
        servico.setEstabelecimento(estabelecimento);

        Servico servicoAtualizado = servicoRepository.save(servico);
        return modelMapper.map(servicoAtualizado, ServicoDTO.class);
    }

    public void excluirServico(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        servicoRepository.delete(servico);
    }
} 
