package backend.com.example.finaceiro.gerenciador_financeiro.dto; // Verifique seu pacote

import java.math.BigDecimal;

public record DadosGraficoGastosDTO(
        String categoria,
        BigDecimal totalGasto
) {
}