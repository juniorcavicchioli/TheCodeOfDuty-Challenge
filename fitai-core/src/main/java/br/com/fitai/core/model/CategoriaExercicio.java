package br.com.fitai.core.model;

import br.com.fitai.core.controller.CategoriaExercicioController;
import br.com.fitai.core.controller.UsuarioController;
import jakarta.persistence.*;
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
public class CategoriaExercicio {
    @Id
    @Column(name = "id_categoria", nullable = false)
    private Long id;
    private String nome;

    public EntityModel<CategoriaExercicio> toEntityModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(CategoriaExercicioController.class).show(id)).withSelfRel(),
                linkTo(methodOn(CategoriaExercicioController.class).destroy(id)).withRel("delete"),
                linkTo(methodOn(CategoriaExercicioController.class).index(Pageable.unpaged())).withRel("all")
        );
    }
}
