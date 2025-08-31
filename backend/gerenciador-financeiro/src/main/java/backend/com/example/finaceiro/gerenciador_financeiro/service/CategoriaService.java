package backend.com.example.finaceiro.gerenciador_financeiro.service; // Verifique o seu pacote

import backend.com.example.finaceiro.gerenciador_financeiro.model.Categoria;
import backend.com.example.finaceiro.gerenciador_financeiro.model.TipoTransacao;
import backend.com.example.finaceiro.gerenciador_financeiro.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Método atualizado para listar categorias filtrando por tipo
    public List<Categoria> listar(TipoTransacao tipo) {
        // Usa o novo método do repositório para buscar filtrando por tipo
        return categoriaRepository.findByTipo(tipo);
    }
}