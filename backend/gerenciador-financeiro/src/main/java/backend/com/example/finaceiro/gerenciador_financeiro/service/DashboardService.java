package backend.com.example.finaceiro.gerenciador_financeiro.service; // Verifique seu pacote

import backend.com.example.finaceiro.gerenciador_financeiro.dto.DadosGraficoGastosDTO;
import backend.com.example.finaceiro.gerenciador_financeiro.model.Usuario;
import backend.com.example.finaceiro.gerenciador_financeiro.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    public List<DadosGraficoGastosDTO> getGastosPorCategoria() {
        var usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return transacaoRepository.findGastosPorCategoria(usuario);
    }
}