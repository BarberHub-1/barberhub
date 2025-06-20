package br.barberhub.backendApplication.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("ESTABELECIMENTO")
@ToString(exclude = {"horario", "servicos", "profissionais"})
public class Estabelecimento extends Usuario {

    @NotBlank(message = "O nome do proprietário é obrigatório")
    @Size(min = 3, max = 100)
    @Column(name = "nome_proprietario")
    private String nomeProprietario;

    @NotBlank(message = "O nome do estabelecimento é obrigatório")
    @Size(min = 3, max = 100)
    @Column(name = "nome_estabelecimento")
    private String nomeEstabelecimento;

    @NotBlank(message = "O CNPJ é obrigatório")
    @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}", message = "CNPJ inválido")
    @Column(unique = true, columnDefinition = "VARCHAR(18)")
    private String cnpj;

    private Integer numero;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    
    @Column(columnDefinition = "LONGTEXT")
    private String foto;

    @NotBlank
    @Pattern(regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}", message = "Telefone inválido")
    private String telefone;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusCadastro status;

    @OneToMany(mappedBy = "estabelecimento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioFuncionamento> horario = new ArrayList<>();

    @OneToMany(mappedBy = "estabelecimento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Servico> servicos = new ArrayList<>();

    @OneToMany(mappedBy = "estabelecimento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Profissional> profissionais = new ArrayList<>();

    public void addHorario(HorarioFuncionamento horario) {
        this.horario.add(horario);
        horario.setEstabelecimento(this);
    }

    public void removeHorario(HorarioFuncionamento horario) {
        this.horario.remove(horario);
        horario.setEstabelecimento(null);
    }
}
