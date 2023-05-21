package br.com.fitai.core.model;

import br.com.fitai.core.controller.RestricaoAlimentarController;
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
public class RestricaoAlimentar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_restricao", nullable = false)
    private Long id;

    private Integer grau;
    private String tipoRestricao;

    @ManyToOne
    @NotNull(message = "O id do usuário é obrigatório")
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario; // relação

    @ManyToOne
    @NotNull(message = "O id da lista é obrigatório")
    @JoinColumn(name = "id_lista_restricao", nullable = false)
    private ListaRestricoes listaRestricoes;

    public EntityModel<RestricaoAlimentar> toEntityModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(RestricaoAlimentarController.class).show(id)).withSelfRel(),
                linkTo(methodOn(RestricaoAlimentarController.class).destroy(id)).withRel("delete"),
                linkTo(methodOn(RestricaoAlimentarController.class).index(Pageable.unpaged())).withRel("all")
        );
    }
}
