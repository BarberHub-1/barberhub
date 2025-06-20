package br.barberhub.backendApplication.dto;

import br.barberhub.backendApplication.model.StatusCadastro;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class EstabelecimentoDTO extends UsuarioDTO {
    private String nomeProprietario;
    private String nomeEstabelecimento;
    private String cnpj;
    private Integer numero;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String telefone;
    private String foto;
    private StatusCadastro status;
    private List<HorarioFuncionamentoDTO> horario;
    private String descricao;
    private List<String> servicos;
    private Double notaMedia;
    private Integer quantidadeAvaliacoes;
}
