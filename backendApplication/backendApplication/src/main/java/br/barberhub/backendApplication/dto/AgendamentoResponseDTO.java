package br.barberhub.backendApplication.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.barberhub.backendApplication.model.StatusAgendamento;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoResponseDTO {
	public enum Status{
		AGENDADA, CANCELADA, CONCLUIDA
    }
	
	private Long id;
    private LocalDateTime dateTime;
    private double price;
    private double discount;
    
    @NotNull(message = "O cliente é obrigatório")
    private Long clienteId;
    
    @NotNull(message = "O estabelecimento é obrigatório")
    private Long estabelecimentoId;
    private String estabelecimentoNome;

    @NotNull(message = "A data e hora são obrigatórias")
    @Future(message = "A data e hora devem ser futuras")
    private LocalDateTime dataHora;

    private StatusAgendamento statusAgendamento;
    
    private AvaliacaoDTO avaliacao;
    
    private List<ServicoDTO> servicosDetalhes = new ArrayList<>();
}
