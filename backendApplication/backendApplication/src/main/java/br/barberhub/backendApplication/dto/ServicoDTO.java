package br.barberhub.backendApplication.dto;

import br.barberhub.backendApplication.model.TipoServico;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicoDTO {

    private Long id;

    @NotNull(message = "O ID do estabelecimento é obrigatório")
    private Long estabelecimentoId;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(min = 10, max = 500, message = "A descrição deve ter entre 10 e 500 caracteres")
    private String descricao;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.0", message = "O preço deve ser maior ou igual a zero")
    private Double preco;

    @NotNull(message = "A duração em minutos é obrigatória")
    @Min(value = 1, message = "A duração deve ser maior que zero")
    private Integer duracaoMinutos;

    @NotNull(message = "O tipo do serviço é obrigatório")
    private TipoServico tipo;
}
