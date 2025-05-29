package br.barberhub.backendApplication.dto;

import br.barberhub.backendApplication.model.DiaSemana;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class HorarioFuncionamentoDTO {
    private Long id;
    
    @NotNull(message = "O dia da semana é obrigatório")
    private DiaSemana diaSemana;
    
    @NotNull(message = "O horário de abertura é obrigatório")
    @Pattern(regexp = "^([01]?\\d|2[0-3]):[0-5]\\d$", message = "Horário de abertura inválido (formato HH:mm)")
    private String horarioAbertura;
    
    @NotNull(message = "O horário de fechamento é obrigatório")
    @Pattern(regexp = "^([01]?\\d|2[0-3]):[0-5]\\d$", message = "Horário de fechamento inválido (formato HH:mm)")
    private String horarioFechamento;
}
