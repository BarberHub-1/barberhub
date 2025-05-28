package br.barberhub.backendApplication.dto;

import java.time.LocalDateTime;
import br.barberhub.backendApplication.model.Profissional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisponibilidadeDTO {
	private Long id;

    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;

    private Profissional profissional;

}
