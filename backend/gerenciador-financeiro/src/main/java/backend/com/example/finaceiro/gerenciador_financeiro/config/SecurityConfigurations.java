package backend.com.example.finaceiro.gerenciador_financeiro.config;

// Imports existentes
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Novos imports para a configuração de CORS
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration // Anotação para marcar esta classe como uma classe de configuração do Spring
@EnableWebSecurity // Anotação para habilitar a configuração de segurança do Spring Security
public class SecurityConfigurations {

    @Bean // Anotação para marcar este método como um bean gerenciado pelo Spring
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configuração principal da cadeia de filtros de segurança do Spring
        return http
                .csrf(csrf -> csrf.disable()) // Desabilita a proteção CSRF, pois usaremos tokens JWT (API stateless)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configura a política de sessão para ser sem estado (stateless)
                .authorizeHttpRequests(req -> {
                    // Configura as permissões de acesso para os endpoints
                    req.requestMatchers(HttpMethod.POST, "/auth/login").permitAll(); // Permite acesso público ao endpoint de login
                    req.requestMatchers(HttpMethod.POST, "/auth/register").permitAll(); // Permite acesso público ao endpoint de registro
                    req.anyRequest().authenticated(); // Exige autenticação para todas as outras requisições
                })
                // ADIÇÃO CRÍTICA: Integra a configuração de CORS definida no bean 'corsConfigurationSource'
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .build(); // Constrói o objeto SecurityFilterChain
    }

    @Bean // Expõe o AuthenticationManager como um Bean para ser usado no nosso AuthController
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        // Obtém e retorna o AuthenticationManager padrão do Spring Security
        return configuration.getAuthenticationManager();
    }

    @Bean // Define o algoritmo de criptografia de senhas que será usado na aplicação
    public PasswordEncoder passwordEncoder() {
        // Usa o BCrypt, que é o padrão recomendado e mais seguro atualmente
        return new BCryptPasswordEncoder();
    }

    // NOVO BEAN: Define as configurações de CORS para toda a aplicação
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // Cria um objeto de configuração de CORS
        CorsConfiguration configuration = new CorsConfiguration();
        // Define quais origens (endereços de frontend) são permitidas. No nosso caso, apenas o React em localhost:3000
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        // Define quais métodos HTTP (GET, POST, etc.) são permitidos a partir dessas origens
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Permite que todos os cabeçalhos (headers) sejam enviados na requisição
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // Permite que o frontend envie credenciais (como tokens)
        configuration.setAllowCredentials(true);

        // Cria uma fonte de configuração de CORS baseada em URL
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplica as configurações de CORS que definimos acima para todos os endpoints da nossa API ("/**")
        source.registerCorsConfiguration("/**", configuration);
        
        // Retorna a fonte de configuração para que o Spring Security a utilize
        return source;
    }
}