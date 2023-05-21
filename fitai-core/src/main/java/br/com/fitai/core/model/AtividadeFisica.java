package br.com.fitai.core.model;

import br.com.fitai.core.controller.AtividadeFisicaController;
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
public class AtividadeFisica {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_atividade", nullable = false)
    private Long id;

    @ManyToOne
    @NotNull(message = "O id do usuário é obrigatório")
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario; // relação

    public EntityModel<AtividadeFisica> toEntityModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(AtividadeFisicaController.class).show(id)).withSelfRel(),
                linkTo(methodOn(AtividadeFisicaController.class).destroy(id)).withRel("delete"),
                linkTo(methodOn(AtividadeFisicaController.class).index(Pageable.unpaged())).withRel("all")
        );
    }
}
