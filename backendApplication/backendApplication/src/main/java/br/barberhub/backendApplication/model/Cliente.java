package br.barberhub.backendApplication.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("CLIENTE")
public class Cliente extends Usuario {

    private String nome;
    @Column(columnDefinition = "VARCHAR(14)")
    private String cpf;
    private String telefone;
    private Integer numero;
    private Integer rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String foto;
}
