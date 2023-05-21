package br.com.fitai.core.model;

import br.com.fitai.core.controller.CategoriaRefeicaoController;
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
public class CategoriaRefeicao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_categoria", nullable = false)
    private Long id;

    private String categoria;

    public EntityModel<CategoriaRefeicao> toEntityModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(CategoriaRefeicaoController.class).show(id)).withSelfRel(),
                linkTo(methodOn(CategoriaRefeicaoController.class).destroy(id)).withRel("delete"),
                linkTo(methodOn(CategoriaRefeicaoController.class).index(Pageable.unpaged())).withRel("all")
        );
    }
}
