package backend.com.example.finaceiro.gerenciador_financeiro.controller; // Verifique o seu pacote

import backend.com.example.finaceiro.gerenciador_financeiro.model.Categoria;
import backend.com.example.finaceiro.gerenciador_financeiro.model.TipoTransacao;
import backend.com.example.finaceiro.gerenciador_financeiro.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @GetMapping
    public ResponseEntity<List<Categoria>> listar(@RequestParam("tipo") TipoTransacao tipo) {
        var listaDeCategorias = service.listar(tipo);
        return ResponseEntity.ok(listaDeCategorias);
    }
}