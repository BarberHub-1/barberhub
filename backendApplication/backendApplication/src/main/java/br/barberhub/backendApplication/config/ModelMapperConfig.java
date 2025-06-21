package br.barberhub.backendApplication.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import br.barberhub.backendApplication.dto.ClienteDTO;
import br.barberhub.backendApplication.dto.EstabelecimentoDTO;
import br.barberhub.backendApplication.dto.HorarioFuncionamentoDTO;
import br.barberhub.backendApplication.model.Cliente;
import br.barberhub.backendApplication.model.Estabelecimento;
import br.barberhub.backendApplication.model.HorarioFuncionamento;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
public class ModelMapperConfig {

    @PostConstruct
    public void init() {
        // Define o timezone padrão da aplicação
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
        System.out.println("Timezone da aplicação definido como: " + TimeZone.getDefault().getID());
    }

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
                mapper.map(Estabelecimento::getRua, EstabelecimentoDTO::setRua);
                mapper.map(Estabelecimento::getNumero, EstabelecimentoDTO::setNumero);
                mapper.map(Estabelecimento::getBairro, EstabelecimentoDTO::setBairro);
                mapper.map(Estabelecimento::getCidade, EstabelecimentoDTO::setCidade);
                mapper.map(Estabelecimento::getEstado, EstabelecimentoDTO::setEstado);
                mapper.map(Estabelecimento::getCep, EstabelecimentoDTO::setCep);
                mapper.map(Estabelecimento::getTelefone, EstabelecimentoDTO::setTelefone);
                mapper.map(Estabelecimento::getFoto, EstabelecimentoDTO::setFoto);
                mapper.map(Estabelecimento::getStatus, EstabelecimentoDTO::setStatus);
                mapper.map(Estabelecimento::getHorario, EstabelecimentoDTO::setHorario);
                mapper.map(Estabelecimento::getDescricao, EstabelecimentoDTO::setDescricao);
                mapper.map(Estabelecimento::getServicos, EstabelecimentoDTO::setServicos);
            });
        
        // Configuração específica para EstabelecimentoDTO -> Estabelecimento (ignora ID)
        modelMapper.createTypeMap(EstabelecimentoDTO.class, Estabelecimento.class)
            .addMappings(mapper -> {
                mapper.skip(Estabelecimento::setId); // Ignora o ID durante o mapeamento
                mapper.map(EstabelecimentoDTO::getNomeEstabelecimento, Estabelecimento::setNomeEstabelecimento);
                mapper.map(EstabelecimentoDTO::getNomeProprietario, Estabelecimento::setNomeProprietario);
                mapper.map(EstabelecimentoDTO::getCnpj, Estabelecimento::setCnpj);
                mapper.map(EstabelecimentoDTO::getRua, Estabelecimento::setRua);
                mapper.map(EstabelecimentoDTO::getNumero, Estabelecimento::setNumero);
                mapper.map(EstabelecimentoDTO::getBairro, Estabelecimento::setBairro);
                mapper.map(EstabelecimentoDTO::getCidade, Estabelecimento::setCidade);
                mapper.map(EstabelecimentoDTO::getEstado, Estabelecimento::setEstado);
                mapper.map(EstabelecimentoDTO::getCep, Estabelecimento::setCep);
                mapper.map(EstabelecimentoDTO::getTelefone, Estabelecimento::setTelefone);
                mapper.map(EstabelecimentoDTO::getFoto, Estabelecimento::setFoto);
                mapper.map(EstabelecimentoDTO::getDescricao, Estabelecimento::setDescricao);
            });
        
        // Configuração específica para HorarioFuncionamento
        modelMapper.createTypeMap(HorarioFuncionamentoDTO.class, HorarioFuncionamento.class)
            .addMappings(mapper -> {
                mapper.skip(HorarioFuncionamento::setId); // Ignora o ID durante o mapeamento
                mapper.map(HorarioFuncionamentoDTO::getDiaSemana, HorarioFuncionamento::setDiaSemana);
                mapper.map(HorarioFuncionamentoDTO::getHorarioAbertura, HorarioFuncionamento::setHorarioAbertura);
                mapper.map(HorarioFuncionamentoDTO::getHorarioFechamento, HorarioFuncionamento::setHorarioFechamento);
            });
        
        // Configuração específica para Cliente
        modelMapper.createTypeMap(ClienteDTO.class, Cliente.class)
            .addMappings(mapper -> {
                mapper.skip(Cliente::setId); // Ignora o ID durante o mapeamento
                mapper.map(ClienteDTO::getNome, Cliente::setNome);
                mapper.map(ClienteDTO::getCpf, Cliente::setCpf);
                mapper.map(ClienteDTO::getTelefone, Cliente::setTelefone);
                mapper.map(ClienteDTO::getRua, Cliente::setRua);
                mapper.map(ClienteDTO::getNumero, Cliente::setNumero);
                mapper.map(ClienteDTO::getBairro, Cliente::setBairro);
                mapper.map(ClienteDTO::getCidade, Cliente::setCidade);
                mapper.map(ClienteDTO::getEstado, Cliente::setEstado);
                mapper.map(ClienteDTO::getCep, Cliente::setCep);
            });
            
        modelMapper.createTypeMap(Cliente.class, ClienteDTO.class)
            .addMappings(mapper -> {
                mapper.map(Cliente::getNome, ClienteDTO::setNome);
                mapper.map(Cliente::getCpf, ClienteDTO::setCpf);
                mapper.map(Cliente::getTelefone, ClienteDTO::setTelefone);
                mapper.map(Cliente::getRua, ClienteDTO::setRua);
                mapper.map(Cliente::getNumero, ClienteDTO::setNumero);
                mapper.map(Cliente::getBairro, ClienteDTO::setBairro);
                mapper.map(Cliente::getCidade, ClienteDTO::setCidade);
                mapper.map(Cliente::getEstado, ClienteDTO::setEstado);
                mapper.map(Cliente::getCep, ClienteDTO::setCep);
                mapper.map(Cliente::getFoto, ClienteDTO::setFoto);
            });
        
        return modelMapper;
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS);
        return mapper;
    }
} 