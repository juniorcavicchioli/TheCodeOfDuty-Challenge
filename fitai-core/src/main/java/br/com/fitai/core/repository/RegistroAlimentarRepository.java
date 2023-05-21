package br.com.fitai.core.repository;

import br.com.fitai.core.model.RegistroAlimentar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroAlimentarRepository extends JpaRepository<RegistroAlimentar, Long> {
}
