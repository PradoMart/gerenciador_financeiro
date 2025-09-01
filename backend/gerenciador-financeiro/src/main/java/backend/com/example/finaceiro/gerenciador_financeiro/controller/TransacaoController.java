package backend.com.example.finaceiro.gerenciador_financeiro.controller; // Verifique o nome do seu pacote

import backend.com.example.finaceiro.gerenciador_financeiro.dto.DadosAtualizacaoTransacaoDTO;
import backend.com.example.finaceiro.gerenciador_financeiro.dto.DadosCadastroTransacaoDTO;
import backend.com.example.finaceiro.gerenciador_financeiro.dto.DadosDetalhamentoTransacaoDTO;
import backend.com.example.finaceiro.gerenciador_financeiro.service.TransacaoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService service;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoTransacaoDTO> cadastrar(@RequestBody @Valid DadosCadastroTransacaoDTO dados) {
        var transacao = service.cadastrar(dados);
        var dto = new DadosDetalhamentoTransacaoDTO(transacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoTransacaoDTO>> listar() {
        var listaDeTransacoesDTO = service.listar();
        return ResponseEntity.ok(listaDeTransacoesDTO);
    }

    @DeleteMapping("/{id}") // A URL será, por exemplo, /transacoes/4
    @Transactional // Garante que a operação de exclusão seja concluída com sucesso no banco
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        // Retorna uma resposta 204 No Content, que é o padrão para exclusões bem-sucedidas.
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoTransacaoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoTransacaoDTO dados) {
        var transacaoAtualizada = service.atualizar(id, dados);
        // Retornamos o DTO de detalhamento para manter a consistência e segurança da resposta
        return ResponseEntity.ok(new DadosDetalhamentoTransacaoDTO(transacaoAtualizada));
    }

}