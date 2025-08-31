package backend.com.example.finaceiro.gerenciador_financeiro.dto;

import backend.com.example.finaceiro.gerenciador_financeiro.model.Categoria;

public record DadosDetalhamentoCategoriaDTO(Long id, String nome) {
    public DadosDetalhamentoCategoriaDTO(Categoria categoria) {
        this(categoria.getId(), categoria.getNome());
    }
}