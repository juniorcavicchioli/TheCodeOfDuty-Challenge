package br.com.fitai.core.model;

import br.com.fitai.core.controller.IntencaoController;
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
public class Intencao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_intencao", nullable = false)
    private Long id;

    private String preferecia;
    private String perguntas;
    private String respostas;
    private String intencao;

    @ManyToOne
    @NotNull(message = "O id do usuário é obrigatório")
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario; // relação

    public EntityModel<Intencao> toEntityModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(IntencaoController.class).show(id)).withSelfRel(),
                linkTo(methodOn(IntencaoController.class).destroy(id)).withRel("delete"),
                linkTo(methodOn(IntencaoController.class).index(Pageable.unpaged())).withRel("all")
        );
    }
}
