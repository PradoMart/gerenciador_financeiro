import React, { useState } from 'react'; // Importa useState para gerenciar o estado dos inputs
import { useNavigate, Link } from 'react-router-dom'; // Importa useNavigate para navegação programática e Link para navegação declarativa
import api from '../services/api'; // Importa a instância do axios configurada

function LoginPage() {
    const [email, setEmail] = useState(''); // Estado para o email do usuário
    const [senha, setSenha] = useState(''); // Estado para a senha do usuário
    const [error, setError] = useState(''); // Estado para mensagens de erro
    const navigate = useNavigate(); // Hook para navegação programática

    // Função para lidar com o envio do formulário
    const handleSubmit = async (e) => {
        e.preventDefault(); // Previne o comportamento padrão do formulário
        setError(''); // Limpa mensagens de erro anteriores
        try {
            const response = await api.post('/auth/login', { email, senha }); // Faz a requisição para login
            const { token } = response.data; // Extrai o token da resposta
            
            localStorage.setItem('authToken', token); // Armazena o token no localStorage
            api.defaults.headers.common['Authorization'] = `Bearer ${token}`; // Configura o token para futuras requisições

            navigate('/dashboard'); // Redireciona para a página do dashboard após login bem-sucedido
        } catch (err) {
            setError('Erro ao fazer login. Verifique seu e-mail e senha.'); // Define a mensagem de erro em caso de falha
            console.error(err); // Loga o erro para depuração
        }
    };

    return (
        <div>
            <h2>Login</h2>
            <form onSubmit={handleSubmit}> {/* Chama handleSubmit ao enviar o formulário */}
                <div>
                    <label>Email:</label>
                    {/* Input controlado para o email */}
                    <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                </div>
                <div>
                    <label>Senha:</label>
                    {/* Input controlado para a senha */}
                    <input type="password" value={senha} onChange={(e) => setSenha(e.target.value)} required />
                </div>
                {error && <p style={{ color: 'red' }}>{error}</p>} {/* Exibe a mensagem de erro, se houver */}
                <button type="submit">Entrar</button> {/* Botão para enviar o formulário */}
            </form>
            <p>Não tem uma conta? <Link to="/register">Cadastre-se</Link></p> {/* Link para a página de registro */}
        </div>
    );
}
export default LoginPage; // Exporta o componente LoginPage