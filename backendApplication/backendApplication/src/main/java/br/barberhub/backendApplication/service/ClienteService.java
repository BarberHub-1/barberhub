package br.barberhub.backendApplication.service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import br.barberhub.backendApplication.dto.ClienteDTO;
import br.barberhub.backendApplication.model.Cliente;
import br.barberhub.backendApplication.model.StatusCadastro;
import br.barberhub.backendApplication.repository.ClienteRepository;
import jakarta.validation.Valid;


@Service
@Validated
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public ClienteDTO cadastrarCliente(@Valid ClienteDTO clienteDTO) {
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        
        // Criptografa a senha
        cliente.setSenha(passwordEncoder.encode(clienteDTO.getSenha()));
        
        Cliente clienteSalvo = clienteRepository.save(cliente);
        
        ClienteDTO response = modelMapper.map(clienteSalvo, ClienteDTO.class);
        response.setSenha(null); // Não retorna a senha na resposta
        return response;
    }

    public List<ClienteDTO> listarClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(cliente -> {
                    ClienteDTO dto = modelMapper.map(cliente, ClienteDTO.class);
                    dto.setSenha(null); // Não retorna a senha
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public ClienteDTO buscarClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        ClienteDTO dto = modelMapper.map(cliente, ClienteDTO.class);
        dto.setSenha(null); // Não retorna a senha
        return dto;
    }

    public ClienteDTO atualizarCliente(Long id, ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        
        modelMapper.map(clienteDTO, cliente);
        
        // Se uma nova senha foi fornecida, criptografa ela
        if (clienteDTO.getSenha() != null && !clienteDTO.getSenha().isEmpty()) {
            cliente.setSenha(passwordEncoder.encode(clienteDTO.getSenha()));
        }
        
        Cliente clienteAtualizado = clienteRepository.save(cliente);
        ClienteDTO response = modelMapper.map(clienteAtualizado, ClienteDTO.class);
        response.setSenha(null); // Não retorna a senha
        return response;
    }

    public void excluirCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        clienteRepository.delete(cliente);
    }

} 