package br.com.fitai.core.repository;

import br.com.fitai.core.model.Intencao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntencaoRepository extends JpaRepository<Intencao, Long> {
}
