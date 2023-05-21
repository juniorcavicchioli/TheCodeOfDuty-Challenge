package br.com.fitai.core.repository;

import br.com.fitai.core.model.Exercicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExercicioRepository extends JpaRepository<Exercicio, Long> {
}
