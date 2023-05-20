package br.com.fitai.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CategoriaExercicio {
    @Id
    @Column(name = "id_categoria", nullable = false)
    private Long id;
    private String nome;
}
