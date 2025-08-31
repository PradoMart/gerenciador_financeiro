package backend.com.example.finaceiro.gerenciador_financeiro.service; // Verifique o nome do seu pacote

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
        var transacoes = transacaoRepository.findAllByUsuario(usuario);

        // Converte a lista de Entidades (Transacao) para uma lista de DTOs (DadosDetalhamentoTransacaoDTO)
        return transacoes.stream()
                .map(DadosDetalhamentoTransacaoDTO::new)
                .collect(Collectors.toList());
    }
}