package br.barberhub.backendApplication.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import br.barberhub.backendApplication.dto.EstabelecimentoDTO;
import br.barberhub.backendApplication.model.Estabelecimento;
import br.barberhub.backendApplication.model.HorarioFuncionamento;
import br.barberhub.backendApplication.model.Servico;
import br.barberhub.backendApplication.model.StatusCadastro;
import br.barberhub.backendApplication.model.TipoServico;
import br.barberhub.backendApplication.repository.EstabelecimentoRepository;
import br.barberhub.backendApplication.repository.ServicoRepository;
import br.barberhub.backendApplication.repository.AvaliacaoRepository;
import jakarta.validation.Valid;

@Service
@Validated
public class EstabelecimentoService {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public EstabelecimentoDTO cadastrarEstabelecimento(@Valid EstabelecimentoDTO estabelecimentoDTO) {
        Estabelecimento estabelecimento = modelMapper.map(estabelecimentoDTO, Estabelecimento.class);
        
        // Configura o status inicial como PENDENTE
        estabelecimento.setStatus(StatusCadastro.PENDENTE);
        
        // Gera uma senha temporária
        String senhaTemporaria = UUID.randomUUID().toString().substring(0, 8);
        estabelecimento.setSenha(passwordEncoder.encode(senhaTemporaria));
        
        // Salva o estabelecimento primeiro para ter o ID
        Estabelecimento estabelecimentoSalvo = estabelecimentoRepository.save(estabelecimento);
        
        // Processa os serviços
        if (estabelecimentoDTO.getServicos() != null) {
            estabelecimentoDTO.getServicos().forEach(tipoServico -> {
                Servico servico = new Servico();
                servico.setTipo(TipoServico.valueOf(tipoServico));
                servico.setDescricao(getDescricaoServico(TipoServico.valueOf(tipoServico)));
                servico.setPreco(getPrecoPadraoServico(TipoServico.valueOf(tipoServico)));
                servico.setDuracaoMinutos(getDuracaoPadraoServico(TipoServico.valueOf(tipoServico)));
                servico.setEstabelecimento(estabelecimentoSalvo);
                servicoRepository.save(servico);
            });
        }
        
        EstabelecimentoDTO response = modelMapper.map(estabelecimentoSalvo, EstabelecimentoDTO.class);
        response.setSenha(senhaTemporaria); // Inclui a senha temporária na resposta
        return response;
    }

    private String getDescricaoServico(TipoServico tipo) {
        switch (tipo) {
            case CORTE_DE_CABELO:
                return "Corte de cabelo personalizado";
            case BARBA:
                return "Aparo e modelagem de barba";
            case SOBRANCELHA:
                return "Design e modelagem de sobrancelhas";
            case HIDRATACAO:
                return "Hidratação capilar profunda";
            case LUZES:
                return "Aplicação de luzes no cabelo";
            default:
                return "Serviço personalizado";
        }
    }

    private double getPrecoPadraoServico(TipoServico tipo) {
        switch (tipo) {
            case CORTE_DE_CABELO:
                return 50.0;
            case BARBA:
                return 30.0;
            case SOBRANCELHA:
                return 20.0;
            case HIDRATACAO:
                return 40.0;
            case LUZES:
                return 120.0;
            default:
                return 0.0;
        }
    }

    private int getDuracaoPadraoServico(TipoServico tipo) {
        switch (tipo) {
            case CORTE_DE_CABELO:
                return 30;
            case BARBA:
                return 20;
            case SOBRANCELHA:
                return 15;
            case HIDRATACAO:
                return 40;
            case LUZES:
                return 120;
            default:
                return 30;
        }
    }

    public List<EstabelecimentoDTO> listarEstabelecimentos() {
        List<Estabelecimento> estabelecimentos = estabelecimentoRepository.findAll();
        return estabelecimentos.stream()
                .map(estabelecimento -> {
                    EstabelecimentoDTO dto = modelMapper.map(estabelecimento, EstabelecimentoDTO.class);
                    // Buscar avaliações
                    List<br.barberhub.backendApplication.model.Avaliacao> avaliacoes = avaliacaoRepository.findByEstabelecimentoId(estabelecimento.getId());
                    if (!avaliacoes.isEmpty()) {
                        double media = avaliacoes.stream().mapToInt(a -> a.getNota()).average().orElse(0.0);
                        dto.setNotaMedia(media);
                        dto.setQuantidadeAvaliacoes(avaliacoes.size());
                    } else {
                        dto.setNotaMedia(null);
                        dto.setQuantidadeAvaliacoes(0);
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public EstabelecimentoDTO buscarEstabelecimentoPorId(Long id) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));
        // Força o carregamento dos horários
        estabelecimento.getHorario().size();
        EstabelecimentoDTO dto = modelMapper.map(estabelecimento, EstabelecimentoDTO.class);
        List<br.barberhub.backendApplication.model.Avaliacao> avaliacoes = avaliacaoRepository.findByEstabelecimentoId(estabelecimento.getId());
        if (!avaliacoes.isEmpty()) {
            double media = avaliacoes.stream().mapToInt(a -> a.getNota()).average().orElse(0.0);
            dto.setNotaMedia(media);
            dto.setQuantidadeAvaliacoes(avaliacoes.size());
        } else {
            dto.setNotaMedia(null);
            dto.setQuantidadeAvaliacoes(0);
        }
        return dto;
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
        
        Estabelecimento estabelecimentoAtualizado = estabelecimentoRepository.save(estabelecimento);
        return modelMapper.map(estabelecimentoAtualizado, EstabelecimentoDTO.class);
    }

    public void excluirEstabelecimento(Long id) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));
        estabelecimentoRepository.delete(estabelecimento);
    }

    public EstabelecimentoDTO toDTO(Estabelecimento estabelecimento) {
        EstabelecimentoDTO dto = modelMapper.map(estabelecimento, EstabelecimentoDTO.class);
        return dto;
    }

    public Estabelecimento toEntity(EstabelecimentoDTO dto) {
        return modelMapper.map(dto, Estabelecimento.class);
    }
} 