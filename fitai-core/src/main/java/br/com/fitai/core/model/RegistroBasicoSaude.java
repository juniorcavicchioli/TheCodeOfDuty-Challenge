package br.com.fitai.core.model;

import br.com.fitai.core.controller.RegistroBasicoSaudeController;
import br.com.fitai.core.controller.UsuarioController;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;

import java.time.LocalDate;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class RegistroBasicoSaude {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_basico_saude")
    private Long id;
    @NotNull
    private LocalDate dia;
    private Float peso;

    @ManyToOne
    @NotNull(message = "O id do usuário é obrigatório")
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario; // relação

    public EntityModel<RegistroBasicoSaude> toEntityModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(RegistroBasicoSaudeController.class).show(id)).withSelfRel(),
                linkTo(methodOn(RegistroBasicoSaudeController.class).destroy(id)).withRel("delete"),
                linkTo(methodOn(RegistroBasicoSaudeController.class).index(Pageable.unpaged())).withRel("all")
        );
    }
}
