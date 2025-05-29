package br.barberhub.backendApplication.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import br.barberhub.backendApplication.dto.EstabelecimentoDTO;
import br.barberhub.backendApplication.model.Estabelecimento;
import br.barberhub.backendApplication.model.HorarioFuncionamento;
import br.barberhub.backendApplication.model.Servico;
import br.barberhub.backendApplication.repository.EstabelecimentoRepository;
import br.barberhub.backendApplication.repository.ServicoRepository;
import jakarta.validation.Valid;

@Service
@Validated
public class EstabelecimentoService {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public EstabelecimentoDTO cadastrarEstabelecimento(@Valid EstabelecimentoDTO estabelecimentoDTO) {
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
        
        // Força o carregamento dos horários
        estabelecimento.getHorario().size();
        
        return modelMapper.map(estabelecimento, EstabelecimentoDTO.class);
    }

    @Transactional
    public EstabelecimentoDTO atualizarEstabelecimento(Long id, @Valid EstabelecimentoDTO estabelecimentoDTO, MultipartFile foto) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));
        
        // Atualiza os campos básicos
        estabelecimento.setNomeProprietario(estabelecimentoDTO.getNomeProprietario());
        estabelecimento.setNomeEstabelecimento(estabelecimentoDTO.getNomeEstabelecimento());
        estabelecimento.setCnpj(estabelecimentoDTO.getCnpj());
        estabelecimento.setEndereco(estabelecimentoDTO.getEndereco());
        estabelecimento.setCidade(estabelecimentoDTO.getCidade());
        estabelecimento.setCep(estabelecimentoDTO.getCep());
        estabelecimento.setTelefone(estabelecimentoDTO.getTelefone());
        estabelecimento.setDescricao(estabelecimentoDTO.getDescricao());
        
        // Processa a foto se fornecida
        if (foto != null && !foto.isEmpty()) {
            try {
                String fotoBase64 = java.util.Base64.getEncoder().encodeToString(foto.getBytes());
                estabelecimento.setFoto(fotoBase64);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao processar a imagem", e);
            }
        }
        
        // Atualiza os horários
        if (estabelecimentoDTO.getHorario() != null) {
            // Remove todos os horários existentes
            estabelecimento.getHorario().clear();
            
            // Adiciona os novos horários
            estabelecimentoDTO.getHorario().forEach(horarioDTO -> {
                HorarioFuncionamento horario = modelMapper.map(horarioDTO, HorarioFuncionamento.class);
                estabelecimento.addHorario(horario);
            });
        }

        // Atualiza os serviços
        if (estabelecimentoDTO.getServicos() != null) {
            // Remove todos os serviços existentes
            estabelecimento.getServicos().clear();
            
            // Adiciona os novos serviços
            estabelecimentoDTO.getServicos().forEach(servicoId -> {
                try {
                    Servico servico = servicoRepository.findById(Long.parseLong(servicoId))
                        .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
                    estabelecimento.getServicos().add(servico);
                } catch (NumberFormatException e) {
                    // Se não for um número, ignora o serviço
                    System.out.println("ID de serviço inválido: " + servicoId);
                }
            });
        }
        
        Estabelecimento estabelecimentoAtualizado = estabelecimentoRepository.save(estabelecimento);
        return modelMapper.map(estabelecimentoAtualizado, EstabelecimentoDTO.class);
    }

    public void excluirEstabelecimento(Long id) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));
        estabelecimentoRepository.delete(estabelecimento);
    }
} 