package br.barberhub.backendApplication.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClienteDTO extends UsuarioDTO {
    private String nome;
    private String cpf;
    private String telefone;
    private Integer numero;
    private Integer rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String foto;
}
