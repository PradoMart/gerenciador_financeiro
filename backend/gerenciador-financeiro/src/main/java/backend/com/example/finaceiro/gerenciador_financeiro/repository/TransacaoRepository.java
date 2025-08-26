package backend.com.example.finaceiro.gerenciador_financeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import backend.com.example.finaceiro.gerenciador_financeiro.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    
}
