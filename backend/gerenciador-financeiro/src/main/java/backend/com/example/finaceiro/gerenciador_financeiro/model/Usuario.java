package backend.com.example.finaceiro.gerenciador_financeiro.model;

//Importações necessárias
import jakarta.persistence.*; //Importa as anotações JPA necessárias
import lombok.Data; //Importa a anotação Data do Lombok
import lombok.EqualsAndHashCode; //Importa a anotação EqualsAndHashCode do Lombok


//Anotações da classe
@Data //Gera getters, setters, toString, equals e hashCode automaticamente
@EqualsAndHashCode(of = "id") //Gera equals e hashCode baseados no campo "id"
@Entity //Indica que a classe é uma entidade JPA
@Table(name = "usuarios") //Mapeia a entidade para a tabela "usuarios" no banco de dados
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Gera o valor do ID automaticamente
    private Long id;
    private String nome;

    @Column(unique = true) //Garante que o email seja único na tabela
    private String email;
    private String senha;
    
}