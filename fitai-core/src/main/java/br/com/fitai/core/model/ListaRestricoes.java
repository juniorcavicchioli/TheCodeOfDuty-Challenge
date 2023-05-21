package br.com.fitai.core.model;

import br.com.fitai.core.controller.ListaRestricoesController;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
public class ListaRestricoes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_lista_restricao", nullable = false)
    private Long id;
    @NotBlank
    private String alimento;

    public EntityModel<ListaRestricoes> toEntityModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(ListaRestricoesController.class).show(id)).withSelfRel(),
                linkTo(methodOn(ListaRestricoesController.class).destroy(id)).withRel("delete"),
                linkTo(methodOn(ListaRestricoesController.class).index(Pageable.unpaged())).withRel("all")
        );
    }
}
