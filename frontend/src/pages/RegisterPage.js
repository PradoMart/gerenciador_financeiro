import React, { useState } from 'react'; // Importa useState para gerenciar o estado dos inputs
import { useNavigate, Link } from 'react-router-dom'; // Importa useNavigate para navegação programática e Link para navegação declarativa
import api from '../services/api'; // Importa a instância do axios configurada

function RegisterPage() {
    const [nome, setNome] = useState(''); // Estado para o nome do usuário
    const [email, setEmail] = useState(''); // Estado para o email do usuário
    const [senha, setSenha] = useState(''); // Estado para a senha do usuário
    const [error, setError] = useState(''); // Estado para mensagens de erro
    const navigate = useNavigate(); // Hook para navegação programática

    // Função para lidar com o envio do formulário
    const handleSubmit = async (e) => {
        e.preventDefault(); // Previne o comportamento padrão do formulário
        setError(''); // Limpa mensagens de erro anteriores
        try {
            await api.post('/auth/register', { nome, email, senha }); // Faz a requisição para registrar o usuário
            navigate('/login'); // Redireciona para a página de login após registro bem-sucedido
        } catch (err) {
            setError('Erro ao cadastrar. Verifique os dados e tente novamente.'); // Define a mensagem de erro em caso de falha
            console.error(err); // Loga o erro para depuração
        }
    };
    
    return (
        <div>
            <h2>Cadastrar</h2>
            <form onSubmit={handleSubmit}> {/* Chama handleSubmit ao enviar o formulário */}
                <div>
                    <label>Nome:</label>
                    {/* Input controlado para o nome */}
                    <input type="text" value={nome} onChange={(e) => setNome(e.target.value)} required /> 
                </div>
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
                <button type="submit">Cadastrar</button> {/* Botão para enviar o formulário */}
            </form>
                <p>Já tem uma conta? <Link to="/login">Faça login</Link></p> {/* Link para a página de login */}
        </div>
    )
}
export default RegisterPage; // Exporta o componente RegisterPage