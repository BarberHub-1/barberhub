package br.barberhub.backendApplication.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.barberhub.backendApplication.dto.EstabelecimentoDTO;
import br.barberhub.backendApplication.model.Estabelecimento;
import br.barberhub.backendApplication.model.StatusCadastro;
import br.barberhub.backendApplication.repository.EstabelecimentoRepository;
import org.modelmapper.ModelMapper;

@Service
public class AdministradorService {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<EstabelecimentoDTO> listarEstabelecimentosPendentes() {
        List<Estabelecimento> estabelecimentos = estabelecimentoRepository.findByStatus(StatusCadastro.PENDENTE);
        return estabelecimentos.stream()
                .map(estabelecimento -> modelMapper.map(estabelecimento, EstabelecimentoDTO.class))
                .collect(Collectors.toList());
    }

    public EstabelecimentoDTO aprovarEstabelecimento(Long id) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));
        
        estabelecimento.setStatus(StatusCadastro.APROVADO);
        Estabelecimento estabelecimentoAtualizado = estabelecimentoRepository.save(estabelecimento);
        return modelMapper.map(estabelecimentoAtualizado, EstabelecimentoDTO.class);
    }

    public EstabelecimentoDTO rejeitarEstabelecimento(Long id) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));
        
        estabelecimento.setStatus(StatusCadastro.REJEITADO);
        Estabelecimento estabelecimentoAtualizado = estabelecimentoRepository.save(estabelecimento);
        return modelMapper.map(estabelecimentoAtualizado, EstabelecimentoDTO.class);
    }

	/*
	 * public List<Cliente> listarClientesPendentes() { return
	 * clienteRepository.findByStatus(StatusCadastro.AGUARDANDO_APROVACAO); }
	 */

}
