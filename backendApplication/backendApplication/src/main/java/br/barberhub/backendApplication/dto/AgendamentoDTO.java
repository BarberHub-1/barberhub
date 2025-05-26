package br.barberhub.backendApplication.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.barberhub.backendApplication.model.Cliente;
import br.barberhub.backendApplication.model.Estabelecimento;
import br.barberhub.backendApplication.model.Profissional;
import br.barberhub.backendApplication.model.StatusAgendamento;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoDTO {
	public enum Status{
		AGENDADO, CANCELADO, CONCLUIDO
    }
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private LocalDateTime dateTime;
    private double price;
    private double discount;
    
    @NotNull(message = "O cliente é obrigatório")
    private Cliente cliente;
    
    @NotNull(message = "O estabelecimento é obrigatório")
    private Estabelecimento estabelecimento;

    @NotNull(message = "O profissional é obrigatório")
    private Profissional profissional;

    @NotEmpty(message = "A lista de serviços é obrigatória")
    private List<Long> servicos = new ArrayList<>();

    @NotNull(message = "A data e hora são obrigatórias")
    @Future(message = "A data e hora devem ser futuras")
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private StatusAgendamento statusAgendamento;

}
