package br.barberhub.backendApplication.model;


import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cliente extends Usuario {

    private String nome;
    private Long cpf;
    private String telefone;
    private Integer numero;
    private Integer rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String foto;
}
