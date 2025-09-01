package backend.com.example.finaceiro.gerenciador_financeiro.dto; // Verifique seu pacote

import backend.com.example.finaceiro.gerenciador_financeiro.model.TipoTransacao;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

// Note que este DTO é muito parecido com o de cadastro,
// mas é uma boa prática tê-los separados para o caso de futuras diferenças.
public record DadosAtualizacaoTransacaoDTO(
        @NotNull
        String descricao,
        @NotNull
        BigDecimal valor,
        @NotNull
        LocalDate data,
        @NotNull
        TipoTransacao tipo,
        @NotNull
        Long categoriaId
) {
}