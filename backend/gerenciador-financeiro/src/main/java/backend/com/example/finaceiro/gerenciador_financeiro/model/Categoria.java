package backend.com.example.finaceiro.gerenciador_financeiro.model;

//Importações necessárias
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


//Anotações da classe
@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "categorias", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nome", "tipo"}) // Define que a combinação de nome e tipo deve ser única
})
public class Categoria {
    
    @Id //Indica que o campo é a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Gera o valor do ID automaticamente
    private Long id;
    private String nome;

    @Enumerated(EnumType.STRING) //Indica que o campo é um enum e deve ser armazenado como string no banco de dados
    private TipoTransacao tipo; // Tipo de transação (RECEITA ou DESPESA)
}
