package br.com.fitai.core.model;

import br.com.fitai.core.controller.RegistroAlimentarController;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class RegistroAlimentar {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_registro_alimento", nullable = false)
    private Long id;

    @ManyToOne
    @NotNull(message = "O id do usuário é obrigatório")
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario; // relação

    private LocalDateTime horario;

    public EntityModel<RegistroAlimentar> toEntityModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(RegistroAlimentarController.class).show(id)).withSelfRel(),
                linkTo(methodOn(RegistroAlimentarController.class).destroy(id)).withRel("delete"),
                linkTo(methodOn(RegistroAlimentarController.class).index(Pageable.unpaged())).withRel("all")
        );
    }
}
