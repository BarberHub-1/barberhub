package br.barberhub.backendApplication.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;
import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import br.barberhub.backendApplication.dto.EstabelecimentoDTO;
import br.barberhub.backendApplication.dto.HorarioFuncionamentoDTO;
import br.barberhub.backendApplication.model.Estabelecimento;
import br.barberhub.backendApplication.model.HorarioFuncionamento;
import br.barberhub.backendApplication.model.Servico;
import br.barberhub.backendApplication.model.StatusCadastro;
import br.barberhub.backendApplication.model.TipoServico;
import br.barberhub.backendApplication.repository.EstabelecimentoRepository;
import br.barberhub.backendApplication.repository.ServicoRepository;
import br.barberhub.backendApplication.repository.AvaliacaoRepository;
import br.barberhub.backendApplication.repository.HorarioFuncionamentoRepository;
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
    private HorarioFuncionamentoRepository horarioFuncionamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public EstabelecimentoDTO cadastrarEstabelecimento(@Valid EstabelecimentoDTO estabelecimentoDTO) {
        System.out.println("Iniciando cadastro de estabelecimento: " + estabelecimentoDTO.getNomeEstabelecimento());
        
        Estabelecimento estabelecimento = modelMapper.map(estabelecimentoDTO, Estabelecimento.class);
        
        // Limpa as listas para evitar cascade de objetos vazios
        estabelecimento.setHorario(new ArrayList<>());
        estabelecimento.setServicos(new ArrayList<>());
        
        // Configura o status inicial como PENDENTE
        estabelecimento.setStatus(StatusCadastro.PENDENTE);
        
        // Criptografa a senha fornecida pelo usuário
        if (estabelecimentoDTO.getSenha() != null && !estabelecimentoDTO.getSenha().trim().isEmpty()) {
            estabelecimento.setSenha(passwordEncoder.encode(estabelecimentoDTO.getSenha()));
        } else {
            // Gera uma senha temporária apenas se não foi fornecida
            String senhaTemporaria = UUID.randomUUID().toString().substring(0, 8);
            estabelecimento.setSenha(passwordEncoder.encode(senhaTemporaria));
        }
        
        // Salva o estabelecimento primeiro para obter o ID
        System.out.println("Salvando estabelecimento...");
        Estabelecimento estabelecimentoSalvo = estabelecimentoRepository.save(estabelecimento);
        System.out.println("Estabelecimento salvo com ID: " + estabelecimentoSalvo.getId());
        
        // Processa os horários de funcionamento após salvar o estabelecimento
        if (estabelecimentoDTO.getHorario() != null) {
            System.out.println("Processando " + estabelecimentoDTO.getHorario().size() + " horários");
            estabelecimentoDTO.getHorario().forEach(horarioDTO -> {
                try {
                    System.out.println("Processando horário: " + horarioDTO.getDiaSemana() + " - " + horarioDTO.getHorarioAbertura() + " até " + horarioDTO.getHorarioFechamento());
                    
                    // Cria o horário manualmente
                    HorarioFuncionamento horario = new HorarioFuncionamento();
                    horario.setDiaSemana(horarioDTO.getDiaSemana());
                    horario.setHorarioAbertura(horarioDTO.getHorarioAbertura());
                    horario.setHorarioFechamento(horarioDTO.getHorarioFechamento());
                    horario.setEstabelecimento(estabelecimentoSalvo);
                    
                    // Salva o horário
                    HorarioFuncionamento horarioSalvo = horarioFuncionamentoRepository.save(horario);
                    System.out.println("Horário salvo com ID: " + horarioSalvo.getId());
                } catch (Exception e) {
                    System.err.println("Erro ao processar horário: " + e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException("Erro ao processar horário de funcionamento", e);
                }
            });
        }
        
        // Processa os serviços
        if (estabelecimentoDTO.getServicos() != null) {
            System.out.println("Processando " + estabelecimentoDTO.getServicos().size() + " serviços");
            estabelecimentoDTO.getServicos().forEach(tipoServico -> {
                try {
                    System.out.println("Processando serviço: " + tipoServico);
                    TipoServico tipo = TipoServico.valueOf(tipoServico);
                    System.out.println("Tipo convertido: " + tipo);
                    
                    Servico servico = new Servico();
                    servico.setTipo(tipo);
                    
                    String descricao = getDescricaoServico(tipo);
                    System.out.println("Descrição: " + descricao);
                    servico.setDescricao(descricao);
                    
                    double preco = getPrecoPadraoServico(tipo);
                    System.out.println("Preço: " + preco);
                    servico.setPreco(preco);
                    
                    int duracao = getDuracaoPadraoServico(tipo);
                    System.out.println("Duração: " + duracao);
                    servico.setDuracaoMinutos(duracao);
                    
                    servico.setEstabelecimento(estabelecimentoSalvo);
                    
                    Servico servicoSalvo = servicoRepository.save(servico);
                    System.out.println("Serviço salvo com ID: " + servicoSalvo.getId());
                } catch (Exception e) {
                    System.err.println("Erro ao processar serviço: " + e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException("Erro ao processar serviço", e);
                }
            });
        }
        
        // Busca o estabelecimento atualizado com os horários
        Estabelecimento estabelecimentoCompleto = estabelecimentoRepository.findById(estabelecimentoSalvo.getId())
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado após salvar"));
        
        EstabelecimentoDTO response = modelMapper.map(estabelecimentoCompleto, EstabelecimentoDTO.class);
        response.setSenha(estabelecimentoDTO.getSenha()); // Inclui a senha fornecida pelo usuário na resposta
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
        List<Estabelecimento> estabelecimentos = estabelecimentoRepository.findByStatus(StatusCadastro.APROVADO);
        return estabelecimentos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<EstabelecimentoDTO> listarTodosEstabelecimentos() {
        List<Estabelecimento> estabelecimentos = estabelecimentoRepository.findAll();
        return estabelecimentos.stream()
                .map(this::toDTO)
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
        estabelecimento.setRua(estabelecimentoDTO.getRua());
        estabelecimento.setNumero(estabelecimentoDTO.getNumero());
        estabelecimento.setBairro(estabelecimentoDTO.getBairro());
        estabelecimento.setCidade(estabelecimentoDTO.getCidade());
        estabelecimento.setEstado(estabelecimentoDTO.getEstado());
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

    @Transactional
    public void excluirEstabelecimento(Long id) {
        estabelecimentoRepository.deleteById(id);
    }

    @Transactional
    public EstabelecimentoDTO alterarStatusEstabelecimento(Long id, String novoStatus) {
        System.out.println("Buscando estabelecimento com ID: " + id);
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
            .orElseThrow(() -> {
                System.out.println("Estabelecimento não encontrado com ID: " + id);
                return new RuntimeException("Estabelecimento não encontrado");
            });

        System.out.println("Estabelecimento encontrado: " + estabelecimento.getNomeEstabelecimento());
        System.out.println("Alterando status para: " + novoStatus);

        try {
            StatusCadastro status = StatusCadastro.valueOf(novoStatus.toUpperCase());
            estabelecimento.setStatus(status);
            estabelecimentoRepository.save(estabelecimento);
            System.out.println("Status salvo com sucesso.");
            return toDTO(estabelecimento);
        } catch (IllegalArgumentException e) {
            System.err.println("Status inválido: " + novoStatus);
            throw new IllegalArgumentException("Status inválido: " + novoStatus);
        }
    }

    public EstabelecimentoDTO toDTO(Estabelecimento estabelecimento) {
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

    public Estabelecimento toEntity(EstabelecimentoDTO dto) {
        return modelMapper.map(dto, Estabelecimento.class);
    }
} 