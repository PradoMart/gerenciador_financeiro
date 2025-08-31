package backend.com.example.finaceiro.gerenciador_financeiro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import backend.com.example.finaceiro.gerenciador_financeiro.model.Categoria;
import backend.com.example.finaceiro.gerenciador_financeiro.model.TipoTransacao;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByTipo(TipoTransacao tipo);
}
