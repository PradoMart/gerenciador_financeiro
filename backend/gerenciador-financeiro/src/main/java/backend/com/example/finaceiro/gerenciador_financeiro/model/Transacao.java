package backend.com.example.finaceiro.gerenciador_financeiro.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "transacoes")
public class Transacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private BigDecimal valor;
    private LocalDate data;

    @Enumerated(EnumType.STRING) // Armazena o enum como String no banco de dados
    private TipoTransacao tipo;

    @ManyToOne // Relação muitos-para-um com Usuario
    @JoinColumn(name = "usuario_id") // Chave estrangeira para a tabela de usuários
    private Usuario usuario;

    @ManyToOne // Relação muitos-para-um com Categoria
    @JoinColumn(name = "categoria_id") // Chave estrangeira para a tabela de
    private Categoria categoria;
}
