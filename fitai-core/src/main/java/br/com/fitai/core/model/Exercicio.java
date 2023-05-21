package br.com.fitai.core.model;

import br.com.fitai.core.controller.ExercicioController;
import br.com.fitai.core.controller.UsuarioController;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    public EntityModel<Exercicio> toEntityModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(ExercicioController.class).show(id)).withSelfRel(),
                linkTo(methodOn(ExercicioController.class).destroy(id)).withRel("delete"),
                linkTo(methodOn(ExercicioController.class).index(Pageable.unpaged())).withRel("all")
        );
    }
}
