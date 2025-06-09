package br.barberhub.backendApplication.dto;

import br.barberhub.backendApplication.model.StatusCadastro;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstabelecimentoStatusDTO {

    @NotNull(message = "O ID do estabelecimento é obrigatório")
    private Long id;

    @NotNull(message = "O status de cadastro é obrigatório")
    private StatusCadastro statusCadastro;
} 