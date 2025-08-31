package backend.com.example.finaceiro.gerenciador_financeiro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import backend.com.example.finaceiro.gerenciador_financeiro.model.Transacao;
import backend.com.example.finaceiro.gerenciador_financeiro.model.Usuario;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findAllByUsuario(Usuario usuario);
}
