package backend.com.example.finaceiro.gerenciador_financeiro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import backend.com.example.finaceiro.gerenciador_financeiro.dto.DadosGraficoGastosDTO;
import backend.com.example.finaceiro.gerenciador_financeiro.model.Transacao;
import backend.com.example.finaceiro.gerenciador_financeiro.model.Usuario;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findAllByUsuarioOrderByDataDesc(Usuario usuario);

    // A anotação @Query nos permite escrever nossa própria consulta JPQL.
    @Query("""
        SELECT new backend.com.example.finaceiro.gerenciador_financeiro.dto.DadosGraficoGastosDTO(t.categoria.nome, SUM(t.valor))
        FROM Transacao t
        WHERE t.usuario = :usuario AND t.tipo = 'DESPESA'
        GROUP BY t.categoria.nome
        ORDER BY SUM(t.valor) DESC
    """)
    List<DadosGraficoGastosDTO> findGastosPorCategoria(Usuario usuario);
}
