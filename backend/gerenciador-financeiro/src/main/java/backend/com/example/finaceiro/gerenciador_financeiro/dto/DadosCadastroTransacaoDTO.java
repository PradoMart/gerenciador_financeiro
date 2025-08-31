package backend.com.example.finaceiro.gerenciador_financeiro.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import backend.com.example.finaceiro.gerenciador_financeiro.model.TipoTransacao;
import jakarta.validation.constraints.NotNull;

// DTO para cadastro de transações
public record DadosCadastroTransacaoDTO(
    @NotNull
    String descricao,

    @NotNull
    BigDecimal valor,

    @NotNull
    LocalDate data,

    @NotNull
    TipoTransacao tipo,

    @NotNull
    Long categoriaId) {
}
