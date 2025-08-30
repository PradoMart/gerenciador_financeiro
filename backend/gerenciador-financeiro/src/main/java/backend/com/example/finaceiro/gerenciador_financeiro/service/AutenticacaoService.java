package backend.com.example.finaceiro.gerenciador_financeiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import backend.com.example.finaceiro.gerenciador_financeiro.repository.UsuarioRepository;

@Service //Anotação para marcar esta classe como um serviço do Spring
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository; //Repositório para acessar os dados dos usuários

    @Override
    //Construtor para injetar o repositório de usuários
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return repository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!")); //Lança uma exceção se o usuário não for encontrado
    }
    
}
