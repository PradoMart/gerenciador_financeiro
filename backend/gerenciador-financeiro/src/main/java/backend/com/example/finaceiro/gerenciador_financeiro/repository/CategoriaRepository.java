package backend.com.example.finaceiro.gerenciador_financeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import backend.com.example.finaceiro.gerenciador_financeiro.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
}
