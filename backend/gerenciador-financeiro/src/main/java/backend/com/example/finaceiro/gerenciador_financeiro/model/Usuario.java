package backend.com.example.finaceiro.gerenciador_financeiro.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data //Gera getters, setters, toString, equals e hashCode automaticamente
@Entity //Indica que a classe é uma entidade JPA
@Table(name = "usuarios") //Mapeia a entidade para a tabela "usuarios" no banco de dados
public class Usuario {

    
}