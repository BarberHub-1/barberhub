package br.barberhub.backendApplication.dto;

import java.time.LocalDateTime;
import br.barberhub.backendApplication.model.Agendamento;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoDTO {
	
	@NotNull(message = "A nota é obrigatória")
    @Min(value = 1, message = "A nota mínima é 1")
    @Max(value = 5, message = "A nota máxima é 5")
    private Integer nota;
    
    @Size(max = 500, message = "O comentário deve ter no máximo 500 caracteres")
    private String comentario;
    
    @NotNull(message = "O agendamento é obrigatório")
    private Agendamento agendamento;
    
    @NotNull(message = "O profissional é obrigatório")
    private Long profissionalId;
    
	private LocalDateTime dateTime;

}
