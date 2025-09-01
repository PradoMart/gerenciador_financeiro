package backend.com.example.finaceiro.gerenciador_financeiro.service; // Verifique o nome do seu pacote

import backend.com.example.finaceiro.gerenciador_financeiro.dto.DadosAtualizacaoTransacaoDTO;
import backend.com.example.finaceiro.gerenciador_financeiro.dto.DadosCadastroTransacaoDTO;
import backend.com.example.finaceiro.gerenciador_financeiro.dto.DadosDetalhamentoTransacaoDTO;
import backend.com.example.finaceiro.gerenciador_financeiro.model.Transacao;
import backend.com.example.finaceiro.gerenciador_financeiro.model.Usuario;
import backend.com.example.finaceiro.gerenciador_financeiro.repository.CategoriaRepository;
import backend.com.example.finaceiro.gerenciador_financeiro.repository.TransacaoRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    public Transacao cadastrar(DadosCadastroTransacaoDTO dados) {
        // Pega o usuário que está autenticado no contexto de segurança do Spring
        var usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Busca a categoria no banco de dados pelo ID recebido
        var categoria = categoriaRepository.findById(dados.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        // Cria uma nova entidade Transacao com os dados recebidos
        var transacao = new Transacao();
        transacao.setDescricao(dados.descricao());
        transacao.setValor(dados.valor());
        transacao.setData(dados.data());
        transacao.setTipo(dados.tipo());
        transacao.setCategoria(categoria);
        transacao.setUsuario(usuario); // Associa a transação ao usuário logado

        // Salva a transação no banco de dados
        return transacaoRepository.save(transacao);
    }

    public List<DadosDetalhamentoTransacaoDTO> listar() {
        // Pega o usuário que está autenticado
        var usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Usa o novo método do repositório para buscar as transações apenas deste usuário
        var transacoes = transacaoRepository.findAllByUsuarioOrderByDataDesc(usuario);

        // Converte a lista de Entidades (Transacao) para uma lista de DTOs (DadosDetalhamentoTransacaoDTO)
        return transacoes.stream()
                .map(DadosDetalhamentoTransacaoDTO::new)
                .collect(Collectors.toList());
    }

    public void excluir(Long id) {
        // Pega o usuário que está autenticado
        var usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Busca a transação pelo ID fornecido
        var transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));

        // VERIFICAÇÃO DE SEGURANÇA CRÍTICA:
        // Garante que o ID do usuário dono da transação é o mesmo do usuário que está logado.
        // Se não for, lança uma exceção, impedindo a exclusão.
        if (!transacao.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("Acesso negado: esta transação não pertence a você.");
        }

        // Se a verificação passar, exclui a transação
        transacaoRepository.delete(transacao);
    }

    public Transacao atualizar(Long id, DadosAtualizacaoTransacaoDTO dados) {
        var usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Busca a transação original pelo ID
        var transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));

        // Mesma verificação de segurança da exclusão
        if (!transacao.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("Acesso negado: esta transação não pertence a você.");
        }

        // Busca a nova categoria, se o ID foi alterado
        var categoria = categoriaRepository.findById(dados.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        // Atualiza os dados da transação com as informações recebidas
        transacao.setDescricao(dados.descricao());
        transacao.setValor(dados.valor());
        transacao.setData(dados.data());
        transacao.setTipo(dados.tipo());
        transacao.setCategoria(categoria);

        // O método .save() do JpaRepository serve tanto para criar (se o ID é nulo)
        // quanto para atualizar (se o ID já existe).
        return transacaoRepository.save(transacao);
    }

}