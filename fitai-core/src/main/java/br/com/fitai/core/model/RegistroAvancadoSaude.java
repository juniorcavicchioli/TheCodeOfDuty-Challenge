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
}