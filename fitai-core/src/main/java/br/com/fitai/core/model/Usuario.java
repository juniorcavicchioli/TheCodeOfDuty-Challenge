package br.com.fitai.core.model;

import br.com.fitai.core.controller.UsuarioController;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
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
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "O e-mail deve ser preenchido")
    private String email;
    @NotBlank(message = "A senha tem que ter ao menos 8 caracteres")
    @Size(min = 8, message = "A senha tem que ter ao menos 8 caracteres")
    private String senha;
    @NotBlank(message = "O nome deve ser preenchido")
    private String nome;
    @NotBlank(message = "O campo sexo é obrigatório")
    @Pattern(regexp = "[FMN]", message = "O valor do campo sexo deve ser F, M ou N")
    @NotNull(message = "A data de nascimento deve ser preenchida")
    private LocalDate nascimento;
    @NotEmpty(message = "A altura deve ser preenchida")
    @DecimalMin(value = "0.0", message = "A altura mínima permitida é 0")
    @DecimalMax(value = "3.0", message = "A altura máxima permitida é 3")
    private Float altura;
    private Long idRestricao;
    private Long idObjetivo;


    public EntityModel<Usuario> toEntityModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(UsuarioController.class).show(id)).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).destroy(id)).withRel("delete"),
                linkTo(methodOn(UsuarioController.class).index(Pageable.unpaged())).withRel("all")
        );
    }
}
