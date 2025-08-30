package backend.com.example.finaceiro.gerenciador_financeiro.controller;

import backend.com.example.finaceiro.gerenciador_financeiro.dto.DadosCadastroDTO;
import backend.com.example.finaceiro.gerenciador_financeiro.dto.DadosLoginDTO;
import backend.com.example.finaceiro.gerenciador_financeiro.dto.DadosTokenJWT;
import backend.com.example.finaceiro.gerenciador_financeiro.model.Usuario;
import backend.com.example.finaceiro.gerenciador_financeiro.repository.UsuarioRepository;
import backend.com.example.finaceiro.gerenciador_financeiro.service.TokenService;

import jakarta.validation.Valid; //Importa a anotação Valid para validação de dados
import org.springframework.beans.factory.annotation.Autowired; //Importa a anotação Autowired para injeção de dependências
import org.springframework.http.HttpStatus; //Importa a classe HttpStatus para representar códigos de status HTTP
import org.springframework.http.ResponseEntity; //Importa a classe ResponseEntity para construir respostas HTTP
import org.springframework.security.authentication.AuthenticationManager; //Importa a classe AuthenticationManager do Spring Security para gerenciar autenticações
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder; // Importa a interface PasswordEncoder do Spring Security para codificação de senhas
import org.springframework.web.bind.annotation.RequestBody; //Importa a anotação RequestBody para mapear o corpo da requisição para um objeto
import org.springframework.web.bind.annotation.RequestMapping; //Importa a anotação RequestMapping para mapear URLs para métodos do controlador
import org.springframework.web.bind.annotation.RestController; //Importa a anotação RestController para marcar a classe como um controlador REST
import org.springframework.web.bind.annotation.PostMapping; //Importa a anotação PostMapping para mapear requisições HTTP POST



@RestController //Anotação para marcar esta classe como um controlador REST do Spring
@RequestMapping("/auth") //Define o endpoint base para as requisições deste controlador
public class AuthController {
    
    @Autowired //Anotação para injetar automaticamente a dependência
    private AuthenticationManager manager; //Gerenciador de autenticação do Spring Security

    @Autowired
    private UsuarioRepository repository; //Repositório para acessar os dados dos usuários

    @Autowired
    private PasswordEncoder passwordencoder; //Codificador de senhas do Spring Security

    @Autowired
    private TokenService tokenService; //Serviço para geração de tokens JWT

    @PostMapping("/login") //Define o endpoint para login
    public ResponseEntity login(@RequestBody @Valid DadosLoginDTO dados){
       
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha()); //Cria um token de autenticação com o email e senha fornecidos
        var authentication = manager.authenticate(authenticationToken); //Tenta autenticar o usuário com o token criado

        var usuario = (Usuario) authentication.getPrincipal(); //Obtém os detalhes do usuário autenticado
        var tokenJWT = tokenService.gerarToken(usuario); //Gera um token JWT para o usuário autenticado

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT)); //Retorna o token JWT em uma resposta HTTP 200 OK
    }

    @PostMapping("/register") //Define o endpoint para registro de novos usuários
    public ResponseEntity register(@RequestBody @Valid DadosCadastroDTO dados){
        if(repository.findByEmail(dados.email()).isPresent()){
            return ResponseEntity.badRequest().body("Erro: Email já está em uso!"); //Retorna um erro se o email já estiver em uso
        }

        var usuario = new Usuario(); //Cria um novo usuário
        usuario.setNome(dados.nome());  //Define o nome do usuário
        usuario.setEmail(dados.email()); //Define o email do usuário
        usuario.setSenha(passwordencoder.encode(dados.senha())); //Codifica e define a senha do usuário
        repository.save(usuario); //Salva o novo usuário no banco de dados
        return ResponseEntity.status(HttpStatus.CREATED).build(); //Retorna uma resposta HTTP 201 Created
        
    }
}
