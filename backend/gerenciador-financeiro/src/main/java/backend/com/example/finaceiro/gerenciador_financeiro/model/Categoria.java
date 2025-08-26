package backend.com.example.finaceiro.gerenciador_financeiro.model;

//Importações necessárias
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


//Anotações da classe
@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "categorias")
public class Categoria {
    
    @Id //Indica que o campo é a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Gera o valor do ID automaticamente
    private Long id;
    private String nome;
}
