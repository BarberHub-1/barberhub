package br.barberhub.backendApplication.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("ADMINISTRADOR")
public class Administrador extends Usuario {

    private String nome;
    private String cpf;
    private String telefone;
}
