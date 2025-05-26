package br.barberhub.backendApplication.dto;

import br.barberhub.backendApplication.model.StatusCadastro;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class EstabelecimentoDTO extends UsuarioDTO {
    private String nomeProprietario;
    private String nomeEstabelecimento;
    private String cnpj;
    private String endereco;
    private String cidade;
    private String cep;
    private String telefone;
    private StatusCadastro status;
    private List<HorarioFuncionamentoDTO> horario;
}
