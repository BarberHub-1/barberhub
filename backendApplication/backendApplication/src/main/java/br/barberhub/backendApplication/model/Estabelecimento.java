package br.barberhub.backendApplication.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Entity
public class Estabelecimento extends Usuario {

    @NotBlank(message = "O nome do proprietário é obrigatório")
    @Size(min = 3, max = 100)
    private String nomeProprietario;

    @NotBlank(message = "O nome do estabelecimento é obrigatório")
    @Size(min = 3, max = 100)
    private String nomeEstabelecimento;

    @NotBlank(message = "O CNPJ é obrigatório")
    @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}", message = "CNPJ inválido")
    @Column(unique = true)
    private String cnpj;

    @NotBlank
    @Size(min = 5, max = 200)
    private String endereco;

    @NotBlank
    @Size(min = 2, max = 100)
    private String cidade;

    @NotBlank
    @Size(min = 5, max = 10)
    private String cep;
    
    private String foto;

    @NotBlank
    @Pattern(regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}", message = "Telefone inválido")
    private String telefone;

    @Enumerated(EnumType.STRING)
    private StatusCadastro status;

    @OneToMany(mappedBy = "estabelecimento", cascade = CascadeType.ALL)
    private List<HorarioFuncionamento> horario;

    @OneToMany(mappedBy = "estabelecimento", cascade = CascadeType.ALL)
    private List<Servico> servicos;

    @OneToMany(mappedBy = "estabelecimento", cascade = CascadeType.ALL)
    private List<Profissional> profissionais;
}
