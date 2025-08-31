package backend.com.example.finaceiro.gerenciador_financeiro.controller; // Verifique o nome do seu pacote

import backend.com.example.finaceiro.gerenciador_financeiro.dto.DadosCadastroTransacaoDTO;
import backend.com.example.finaceiro.gerenciador_financeiro.dto.DadosDetalhamentoTransacaoDTO;
import backend.com.example.finaceiro.gerenciador_financeiro.service.TransacaoService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
}