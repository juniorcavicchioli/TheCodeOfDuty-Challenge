package br.com.fitai.core.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ExercicioAvancado {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_avancado", nullable = false)
    private Long id;

    private String intensidade;
    private Integer series;
    private Integer repeticoes;

    @OneToOne
    @NotNull(message = "O id do exercicio básico é obrigatório")
    @JoinColumn(name = "id_exercicio", nullable = false)
    private Exercicio exercicio;
}
