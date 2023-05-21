package br.com.fitai.core.model;

import br.com.fitai.core.controller.AlimentoController;
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
public class Alimento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_alimento", nullable = false)
    private Long id;

    private String alimento;

    public EntityModel<Alimento> toEntityModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(AlimentoController.class).show(id)).withSelfRel(),
                linkTo(methodOn(AlimentoController.class).destroy(id)).withRel("delete"),
                linkTo(methodOn(AlimentoController.class).index(Pageable.unpaged())).withRel("all")
        );
    }
}
