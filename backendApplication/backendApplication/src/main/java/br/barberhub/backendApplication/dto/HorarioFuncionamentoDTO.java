package br.barberhub.backendApplication.dto;

import br.barberhub.backendApplication.model.DiaSemana;
import lombok.Data;

@Data
public class HorarioFuncionamentoDTO {
    private Long id;
    private DiaSemana diaSemana;
    private String horarioAbertura;
    private String horarioFechamento;
}
