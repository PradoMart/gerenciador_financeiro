package backend.com.example.finaceiro.gerenciador_financeiro.controller; // Verifique seu pacote

import backend.com.example.finaceiro.gerenciador_financeiro.dto.DadosGraficoGastosDTO;
import backend.com.example.finaceiro.gerenciador_financeiro.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService service;

    @GetMapping("/gastos-por-categoria")
    public ResponseEntity<List<DadosGraficoGastosDTO>> getGastosPorCategoria() {
        var dadosGrafico = service.getGastosPorCategoria();
        return ResponseEntity.ok(dadosGrafico);
    }
}