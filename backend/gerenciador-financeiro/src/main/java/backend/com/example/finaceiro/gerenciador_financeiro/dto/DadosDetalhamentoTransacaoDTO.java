package backend.com.example.finaceiro.gerenciador_financeiro.dto;

import backend.com.example.finaceiro.gerenciador_financeiro.model.TipoTransacao;
import backend.com.example.finaceiro.gerenciador_financeiro.model.Transacao;
import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosDetalhamentoTransacaoDTO(
        Long id,
        String descricao,
        BigDecimal valor,
        LocalDate data,
        TipoTransacao tipo,
        DadosDetalhamentoUsuarioDTO usuario,
        DadosDetalhamentoCategoriaDTO categoria
) {
    public DadosDetalhamentoTransacaoDTO(Transacao transacao) {
        this(
            transacao.getId(),
            transacao.getDescricao(),
            transacao.getValor(),
            transacao.getData(),
            transacao.getTipo(),
            new DadosDetalhamentoUsuarioDTO(transacao.getUsuario()),
            new DadosDetalhamentoCategoriaDTO(transacao.getCategoria())
        );
    }
}