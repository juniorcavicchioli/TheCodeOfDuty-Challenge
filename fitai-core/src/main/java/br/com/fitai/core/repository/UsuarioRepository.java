package br.com.fitai.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fitai.core.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
