package backend.com.example.finaceiro.gerenciador_financeiro.model;

import java.util.List;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//Importações necessárias
import jakarta.persistence.*; //Importa as anotações JPA necessárias
import lombok.Data; //Importa a anotação Data do Lombok
import lombok.EqualsAndHashCode; //Importa a anotação EqualsAndHashCode do Lombok


//Anotações da classe
@Data //Gera getters, setters, toString, equals e hashCode automaticamente
@EqualsAndHashCode(of = "id") //Gera equals e hashCode baseados no campo "id"
@Entity //Indica que a classe é uma entidade JPA
@Table(name = "usuarios") //Mapeia a entidade para a tabela "usuarios" no banco de dados
public class Usuario implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Gera o valor do ID automaticamente
    private Long id;
    private String nome;

    @Column(unique = true) //Garante que o email seja único na tabela
    private String email;
    private String senha;

    //Implementação dos métodos da interface UserDetails (Spring Security usada para autenticação e autorização)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER")); //Retorna a lista de autoridades do usuário
    }

    @Override
    public String getPassword() {
        return this.senha; //Retorna a senha do usuário
    }

    @Override
    public String getUsername() {
        return this.email; //Retorna o email do usuário como nome de usuário
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //Indica que a conta não está expirada
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //Indica que a conta não está bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //Indica que as credenciais não estão expiradas
    }   

    @Override
    public boolean isEnabled() {
        return true; //Indica que a conta está habilitada
    }

    
}