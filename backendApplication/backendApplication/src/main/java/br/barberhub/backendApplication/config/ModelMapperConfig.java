package br.barberhub.backendApplication.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.barberhub.backendApplication.dto.EstabelecimentoDTO;
import br.barberhub.backendApplication.model.Estabelecimento;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        
        // Configuração para ignorar campos nulos
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        
        // Configuração específica para Estabelecimento
        modelMapper.createTypeMap(EstabelecimentoDTO.class, Estabelecimento.class)
            .addMappings(mapper -> {
                mapper.skip(Estabelecimento::setHorario);
                mapper.skip(Estabelecimento::setServicos);
                mapper.skip(Estabelecimento::setProfissionais);
            });
        
        return modelMapper;
    }
} 