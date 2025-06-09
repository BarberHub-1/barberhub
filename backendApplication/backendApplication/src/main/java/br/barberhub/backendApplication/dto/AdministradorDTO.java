package br.barberhub.backendApplication.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdministradorDTO extends UsuarioDTO {
    private String foto;
}
