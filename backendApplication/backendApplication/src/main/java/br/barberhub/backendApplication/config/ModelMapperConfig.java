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
        modelMapper.createTypeMap(EstabelecimentoDTO.class, Estabelecimento.class)
            .addMappings(mapper -> {
                mapper.skip(Estabelecimento::setHorario);
                mapper.skip(Estabelecimento::setServicos);
                mapper.skip(Estabelecimento::setProfissionais);
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