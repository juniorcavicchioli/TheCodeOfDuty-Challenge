package br.com.fitai.core.model;

import br.com.fitai.core.controller.RegistroAvancadoSaudeController;
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
public class RegistroAvancadoSaude {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avancado_saude")
    private Long id;
    private Float massaMuscular;
    private Float circunCintura;
    private Float circunQuadril;
    private Float massaOssea;
    private Float hidratacao;
    private Integer calDiaria;
    private Integer freqCardiacaRepouso;
    private String pressaoArterial;
    private Integer glicose;
    private Float gorduraCorporal;
    private Integer metabolismoBasal;

    @OneToOne
    @NotNull(message = "O id do objetivo básico é obrigatório")
    @JoinColumn(name = "id_basico_saude", nullable = false)
    private RegistroBasicoSaude registroBasicoSaude; // relação

    public EntityModel<RegistroAvancadoSaude> toEntityModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(RegistroAvancadoSaudeController.class).show(id)).withSelfRel(),
                linkTo(methodOn(RegistroAvancadoSaudeController.class).destroy(id)).withRel("delete"),
                linkTo(methodOn(RegistroAvancadoSaudeController.class).index(Pageable.unpaged())).withRel("all")
        );
    }
}
