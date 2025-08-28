package backend.com.example.finaceiro.gerenciador_financeiro.service;

import java.time.Instant; //Importa a classe Instant para manipulação de timestamps
import java.time.LocalDateTime; //Importa a classe LocalDateTime para manipulação de datas e horas
import java.time.ZoneOffset; //Importa a classe ZoneOffset para manipulação de fusos horários

import org.springframework.beans.factory.annotation.Value; //Importa a anotação Value para injetar valores de propriedades
import com.auth0.jwt.JWT; //Importa a classe JWT da biblioteca Auth0 para criação e verificação de tokens JWT
import com.auth0.jwt.algorithms.Algorithm; //Importa a classe Algorithm para definir o algoritmo de assinatura do token
import com.auth0.jwt.exceptions.JWTCreationException; //Importa a exceção JWTCreationException
import org.springframework.stereotype.Service; //Importa a anotação Service para marcar a classe como um serviço do Spring

import backend.com.example.finaceiro.gerenciador_financeiro.model.Usuario;

@Service //Anotação para marcar esta classe como um serviço do Spring
public class TokenService {
    
    @Value("${api.security.token.secret}") //Injeta o valor da propriedade do arquivo application.properties
    private String secret;

    public String gerarToken(Usuario usuario){
        try {
            var algoritmo = Algorithm.HMAC256(secret); //Cria o algoritmo de criptografia HMAC256 com a chave secreta (HMAC - Hash-based Message Authentication Code é um tipo de código de autenticação de mensagem que utiliza funções hash criptográficas)

            return JWT.create() //Inicia a criação do token JWT
                .withIssuer("API Gerenciador Financeiro") //Define o emissor do token
                .withSubject(usuario.getEmail()) //Define o assunto do token como o email do usuário
                .withExpiresAt(dataExpiracao()) //Define a data de expiração do token
                .sign(algoritmo); //Assina o token com o algoritmo definido

        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token JWT", exception); //Lança uma exceção em caso de erro na criação do token
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")); //Define a data de expiração do token para 2 horas a partir do momento atual
    }
}
