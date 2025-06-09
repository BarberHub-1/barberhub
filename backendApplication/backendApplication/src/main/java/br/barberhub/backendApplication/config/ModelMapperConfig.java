package br.barberhub.backendApplication.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.barberhub.backendApplication.dto.ClienteDTO;
import br.barberhub.backendApplication.dto.EstabelecimentoDTO;
import br.barberhub.backendApplication.model.Cliente;
import br.barberhub.backendApplication.model.Estabelecimento;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        
        // Configuração para ignorar campos nulos
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT)
            .setSkipNullEnabled(true);
        
        // Configuração específica para Estabelecimento
        modelMapper.createTypeMap(Estabelecimento.class, EstabelecimentoDTO.class)
            .addMappings(mapper -> {
                mapper.map(Estabelecimento::getNomeEstabelecimento, EstabelecimentoDTO::setNomeEstabelecimento);
                mapper.map(Estabelecimento::getNomeProprietario, EstabelecimentoDTO::setNomeProprietario);
                mapper.map(Estabelecimento::getCnpj, EstabelecimentoDTO::setCnpj);
                mapper.map(Estabelecimento::getEndereco, EstabelecimentoDTO::setEndereco);
                mapper.map(Estabelecimento::getCidade, EstabelecimentoDTO::setCidade);
                mapper.map(Estabelecimento::getCep, EstabelecimentoDTO::setCep);
                mapper.map(Estabelecimento::getTelefone, EstabelecimentoDTO::setTelefone);
                mapper.map(Estabelecimento::getFoto, EstabelecimentoDTO::setFoto);
                mapper.map(Estabelecimento::getStatus, EstabelecimentoDTO::setStatus);
                mapper.map(Estabelecimento::getHorario, EstabelecimentoDTO::setHorario);
                mapper.map(Estabelecimento::getDescricao, EstabelecimentoDTO::setDescricao);
                mapper.map(Estabelecimento::getServicos, EstabelecimentoDTO::setServicos);
            });
        
        // Configuração específica para Cliente
        modelMapper.createTypeMap(ClienteDTO.class, Cliente.class)
            .addMappings(mapper -> {
                mapper.map(ClienteDTO::getCpf, Cliente::setCpf);
                mapper.map(ClienteDTO::getRua, Cliente::setRua);
                mapper.map(ClienteDTO::getNumero, Cliente::setNumero);
            });
            
        modelMapper.createTypeMap(Cliente.class, ClienteDTO.class)
            .addMappings(mapper -> {
                mapper.map(Cliente::getCpf, ClienteDTO::setCpf);
                mapper.map(Cliente::getRua, ClienteDTO::setRua);
                mapper.map(Cliente::getNumero, ClienteDTO::setNumero);
            });
        
        return modelMapper;
    }
} 