package br.com.fitai.core.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
