package br.barberhub.backendApplication.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.barberhub.backendApplication.dto.ProfissionalDTO;
import br.barberhub.backendApplication.model.Estabelecimento;
import br.barberhub.backendApplication.model.Profissional;
import br.barberhub.backendApplication.model.Servico;
import br.barberhub.backendApplication.repository.EstabelecimentoRepository;
import br.barberhub.backendApplication.repository.ProfissionalRepository;
import br.barberhub.backendApplication.repository.ServicoRepository;
import jakarta.transaction.Transactional;


@Service
public class ProfissionalService {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;
    
    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private ModelMapper modelMapper;


    public ProfissionalService(ProfissionalRepository profissionalRepository, ServicoRepository servicoRepository) {
        this.profissionalRepository = profissionalRepository;
        this.servicoRepository = servicoRepository;
    }
    
    @Transactional
    public ProfissionalDTO cadastrarProfissional(ProfissionalDTO profissionalDTO) {
        Profissional profissional = new Profissional();
        profissional.setNome(profissionalDTO.getNome());
        profissional.setEmail(profissionalDTO.getEmail());
        profissional.setTelefone(profissionalDTO.getTelefone());

        // Buscar e setar o estabelecimento
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(profissionalDTO.getEstabelecimentoId())
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));
        profissional.setEstabelecimento(estabelecimento);

        // Buscar e validar os serviços
        if (profissionalDTO.getServicosIds() != null && !profissionalDTO.getServicosIds().isEmpty()) {
            List<Servico> servicos = servicoRepository.findAllById(profissionalDTO.getServicosIds());

            boolean todosValidos = servicos.stream()
                .allMatch(servico -> servico.getEstabelecimento().getId().equals(estabelecimento.getId()));

            if (!todosValidos) {
                throw new IllegalArgumentException("Todos os serviços devem pertencer ao mesmo estabelecimento do profissional.");
            }

            profissional.setServicos(servicos);
        }

        Profissional salvo = profissionalRepository.save(profissional);
        return modelMapper.map(salvo, ProfissionalDTO.class);
    }

    @Transactional
    public ProfissionalDTO salvarProfissionalDTO(ProfissionalDTO dto) {
        Profissional profissional = new Profissional();
        profissional.setNome(dto.getNome());

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(dto.getEstabelecimentoId())
            .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));
        profissional.setEstabelecimento(estabelecimento);

        if (dto.getServicosIds() != null) {
            List<Servico> servicos = servicoRepository.findAllById(dto.getServicosIds());
            profissional.setServicos(servicos);
        }

        Profissional salvo = profissionalRepository.save(profissional);
        return modelMapper.map(salvo, ProfissionalDTO.class);
    }


    public List<ProfissionalDTO> listarProfissionaisPorEstabelecimento(Long estabelecimentoId) {
        List<Profissional> profissionais = profissionalRepository.findByEstabelecimentoId(estabelecimentoId);
        return profissionais.stream()
                .map(profissional -> modelMapper.map(profissional, ProfissionalDTO.class))
                .collect(Collectors.toList());
    }

    public ProfissionalDTO buscarProfissionalPorId(Long id) {
        Profissional profissional = profissionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
        return modelMapper.map(profissional, ProfissionalDTO.class);
    }

    @Transactional
    public ProfissionalDTO atualizarProfissional(Long id, ProfissionalDTO profissionalDTO) {
        Profissional profissional = profissionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
        
        // Atualizar dados básicos
        profissional.setNome(profissionalDTO.getNome());
        profissional.setEmail(profissionalDTO.getEmail());
        profissional.setTelefone(profissionalDTO.getTelefone());

        // Atualizar estabelecimento
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(profissionalDTO.getEstabelecimentoId())
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));
        profissional.setEstabelecimento(estabelecimento);

        // Atualizar serviços
        if (profissionalDTO.getServicosIds() != null) {
            List<Servico> servicos = servicoRepository.findAllById(profissionalDTO.getServicosIds());

            // Validar se todos os serviços pertencem ao mesmo estabelecimento
            boolean todosValidos = servicos.stream()
                .allMatch(servico -> servico.getEstabelecimento().getId().equals(estabelecimento.getId()));

            if (!todosValidos) {
                throw new IllegalArgumentException("Todos os serviços devem pertencer ao mesmo estabelecimento do profissional.");
            }

            profissional.setServicos(servicos);
        }

        Profissional profissionalAtualizado = profissionalRepository.save(profissional);
        return modelMapper.map(profissionalAtualizado, ProfissionalDTO.class);
    }

    public void excluirProfissional(Long id) {
        Profissional profissional = profissionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
        profissionalRepository.delete(profissional);
    }
} 