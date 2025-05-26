package br.barberhub.backendApplication.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.barberhub.backendApplication.model.Estabelecimento;
import br.barberhub.backendApplication.model.StatusCadastro;
import br.barberhub.backendApplication.repository.EstabelecimentoRepository;


@Service
public class AdministradorService {
	@Autowired
    private EstabelecimentoRepository estabelecimentoRepository;


    public List<Estabelecimento> listarEstabelecimentosPendentes() {
        return estabelecimentoRepository.findByStatus(StatusCadastro.AGUARDANDO_APROVACAO);
    }

	/*
	 * public List<Cliente> listarClientesPendentes() { return
	 * clienteRepository.findByStatus(StatusCadastro.AGUARDANDO_APROVACAO); }
	 */

}
