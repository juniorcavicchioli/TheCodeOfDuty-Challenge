package br.com.fitai.core.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Exercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_exercicio", nullable = false)
    private Long id;

    @ManyToOne
    @NotNull(message = "O id da categoria é obrigatório")
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaExercicio categoriaExercicio; // relação

    @ManyToOne
    @NotNull(message = "O id da atividade é obrigatório")
    @JoinColumn(name = "id_atividade", nullable = false)
    private AtividadeFisica atividadeFisica; // relação

    private String nome;
    private LocalTime duracao;
    private String descricao;
}
