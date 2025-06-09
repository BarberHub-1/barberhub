package br.barberhub.backendApplication.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.barberhub.backendApplication.dto.HorarioFuncionamentoDTO;
import br.barberhub.backendApplication.model.HorarioFuncionamento;
import br.barberhub.backendApplication.repository.HorarioFuncionamentoRepository;



@Service
public class HorarioFuncionamentoService {

    @Autowired
    private HorarioFuncionamentoRepository horarioIntervaloRepository;


    @Autowired
    private ModelMapper modelMapper;

    public HorarioFuncionamentoDTO cadastrarIntervalo(HorarioFuncionamentoDTO horarioIntervaloDTO) {
    	HorarioFuncionamento horarioIntervalo = modelMapper.map(horarioIntervaloDTO, HorarioFuncionamento.class);
        

        HorarioFuncionamento horarioIntervaloSalvo = horarioIntervaloRepository.save(horarioIntervalo);
        return modelMapper.map(horarioIntervaloSalvo, HorarioFuncionamentoDTO.class);
    }

    public List<HorarioFuncionamentoDTO> listarIntervalos() {
        List<HorarioFuncionamento> intervalos = horarioIntervaloRepository.findAll();
        return intervalos.stream()
                .map(intervalo -> modelMapper.map(intervalo, HorarioFuncionamentoDTO.class))
                .collect(Collectors.toList());
    }

    public HorarioFuncionamentoDTO buscarIntervaloPorId(Long id) {
    	HorarioFuncionamento intervalo = horarioIntervaloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Intervalo não encontrado"));
        return modelMapper.map(intervalo, HorarioFuncionamentoDTO.class);
    }

    public List<HorarioFuncionamentoDTO> buscarIntervalosPorEstabelecimento(Long estabelecimentoId) {
        List<HorarioFuncionamento> intervalos = horarioIntervaloRepository.findByEstabelecimentoId(estabelecimentoId);
        return intervalos.stream()
                .map(intervalo -> modelMapper.map(intervalo, HorarioFuncionamentoDTO.class))
                .collect(Collectors.toList());
    }

    public void excluirIntervalo(Long id) {
    	HorarioFuncionamento intervalo = horarioIntervaloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Intervalo não encontrado"));
        horarioIntervaloRepository.delete(intervalo);
    }
}