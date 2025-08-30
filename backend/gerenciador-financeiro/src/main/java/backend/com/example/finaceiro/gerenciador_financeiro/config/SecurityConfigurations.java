package backend.com.example.finaceiro.gerenciador_financeiro.config;

import org.springframework.context.annotation.Bean; //Importa a anotação Bean para definir beans gerenciados pelo Spring
import org.springframework.context.annotation.Configuration; //Importa a anotação Configuration para marcar esta classe como uma classe de configuração do Spring
import org.springframework.http.HttpMethod; //Importa a enumeração HttpMethod para definir métodos HTTP

//Importa classes do Spring Security
import org.springframework.security.authentication.AuthenticationManager; //Importa a classe AuthenticationManager do Spring Security para gerenciar autenticações
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; //Importa a classe AuthenticationConfiguration para configurar o gerenciador de autenticação
import org.springframework.security.config.annotation.web.builders.HttpSecurity; //Importa a classe HttpSecurity para configurar a segurança HTTP
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; //Importa a anotação EnableWebSecurity para habilitar a configuração de segurança do Spring Security
import org.springframework.security.config.http.SessionCreationPolicy; //Importa a enumeração SessionCreationPolicy para definir a política de criação de sessão
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; //Importa a classe BCryptPasswordEncoder para codificação de senhas usando o algoritmo BCrypt
import org.springframework.security.crypto.password.PasswordEncoder; //Importa a interface PasswordEncoder do Spring Security para codificação de senhas
import org.springframework.security.web.SecurityFilterChain; //Importa a classe SecurityFilterChain para definir a cadeia de filtros de segurança

@Configuration //Anotação para marcar esta classe como uma classe de configuração do Spring 
@EnableWebSecurity  //Anotação para habilitar a configuração de segurança do Spring Security
public class SecurityConfigurations {
    
    @Bean //Anotação para marcar este método como um bean gerenciado pelo Spring, o Bean é um objeto que é instanciado, montado e gerenciado pelo Spring IoC Container
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Configuração de segurança HTTP que define as regras de segurança para as requisições
        return http.csrf(csrf -> csrf.disable()) //Desabilita a proteção CSRF (Cross-Site Request Forgery)
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Configura a política de criação de sessão para ser sem estado
            .authorizeHttpRequests(req -> {
                req.requestMatchers(HttpMethod.POST, "/auth/login").permitAll(); //Permite todas as requisições POST para o endpoint /auth/login
                req.requestMatchers(HttpMethod.POST, "/auth/register").permitAll(); //Permite todas as requisições POST para o endpoint /auth/register
                req.anyRequest().authenticated(); //Exige autenticação para todas as outras requisições
            })
            .build(); //Constrói a configuração de segurança
    }

    @Bean
    //Configura o AuthenticationManager que é responsável por autenticar os usuários
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager(); //Retorna o gerenciador de autenticação configurado
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //Retorna uma instância do codificador de senhas BCrypt
    }   
}
