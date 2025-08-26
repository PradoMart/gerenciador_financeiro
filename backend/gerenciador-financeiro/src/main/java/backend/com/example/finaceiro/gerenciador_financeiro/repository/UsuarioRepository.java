package backend.com.example.finaceiro.gerenciador_financeiro.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import backend.com.example.finaceiro.gerenciador_financeiro.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email); // Método para encontrar um usuário pelo email
}
