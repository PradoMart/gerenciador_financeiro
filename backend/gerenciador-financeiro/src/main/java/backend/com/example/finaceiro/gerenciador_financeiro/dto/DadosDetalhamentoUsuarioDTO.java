package backend.com.example.finaceiro.gerenciador_financeiro.dto;

import backend.com.example.finaceiro.gerenciador_financeiro.model.Usuario;

public record DadosDetalhamentoUsuarioDTO(Long id, String nome, String email) {
    public DadosDetalhamentoUsuarioDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}