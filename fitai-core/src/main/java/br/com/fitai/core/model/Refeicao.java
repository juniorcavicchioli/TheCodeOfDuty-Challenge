package br.com.fitai.core.model;

import br.com.fitai.core.controller.RefeicaoController;
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
public class Refeicao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_refeicao", nullable = false)
    private Long id;

    @ManyToOne
    @NotNull(message = "O id do registro alimentar é obrigatório")
    @JoinColumn(name = "id_registro_alimento", nullable = false)
    private RegistroAlimentar registroAlimentar; // relação

    @ManyToOne
    @NotNull(message = "O id do alimento é obrigatório")
    @JoinColumn(name = "id_alimento", nullable = false)
    private Alimento alimento; // relação

    @ManyToOne
    @NotNull(message = "O id da categoria é obrigatório")
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaRefeicao categoriaRefeicao; // relação

    private Integer peso;

    public EntityModel<Refeicao> toEntityModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(RefeicaoController.class).show(id)).withSelfRel(),
                linkTo(methodOn(RefeicaoController.class).destroy(id)).withRel("delete"),
                linkTo(methodOn(RefeicaoController.class).index(Pageable.unpaged())).withRel("all")
        );
    }
}
