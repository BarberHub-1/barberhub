package br.barberhub.backendApplication.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "horarios_funcionamento")
@ToString(exclude = "estabelecimento")
public class HorarioFuncionamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O dia da semana é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana")
    private DiaSemana diaSemana;

    @NotNull(message = "O horário de abertura é obrigatório")
    @Pattern(regexp = "^([01]?\\d|2[0-3]):[0-5]\\d$", message = "Horário de abertura inválido (formato HH:mm)")
    @Column(name = "horario_abertura")
    private String horarioAbertura;

    @NotNull(message = "O horário de fechamento é obrigatório")
    @Pattern(regexp = "^([01]?\\d|2[0-3]):[0-5]\\d$", message = "Horário de fechamento inválido (formato HH:mm)")
    @Column(name = "horario_fechamento")
    private String horarioFechamento;

    @ManyToOne
    @JoinColumn(name = "estabelecimento_id", nullable = false)
    private Estabelecimento estabelecimento;
}
