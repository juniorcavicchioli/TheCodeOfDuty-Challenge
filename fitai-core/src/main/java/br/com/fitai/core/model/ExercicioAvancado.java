package br.com.fitai.core.model;

import br.com.fitai.core.controller.ExercicioAvancadoController;
import br.com.fitai.core.controller.UsuarioController;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    public EntityModel<ExercicioAvancado> toEntityModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(ExercicioAvancadoController.class).show(id)).withSelfRel(),
                linkTo(methodOn(ExercicioAvancadoController.class).destroy(id)).withRel("delete"),
                linkTo(methodOn(ExercicioAvancadoController.class).index(Pageable.unpaged())).withRel("all")
        );
    }
}
