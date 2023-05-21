package br.com.fitai.core.model;

import br.com.fitai.core.controller.ObjetivoController;
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
public class Objetivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_objetivo")
    private Long id;
    @NotNull
    private LocalDate dia;
    private Float peso;

    @OneToOne
    @NotNull(message = "O id do usuário é obrigatório")
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario; // relação

    @OneToOne
    @NotNull(message = "O id do objetivo básico é obrigatório")
    @JoinColumn(name = "id_objetivo_avancado", nullable = false)
    private ObjetivoAvancado objetivoAvancado; // relação

    public EntityModel<Objetivo> toEntityModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(ObjetivoController.class).show(id)).withSelfRel(),
                linkTo(methodOn(ObjetivoController.class).destroy(id)).withRel("delete"),
                linkTo(methodOn(ObjetivoController.class).index(Pageable.unpaged())).withRel("all")
        );
    }
}
