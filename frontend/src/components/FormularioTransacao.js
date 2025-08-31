import React, { useState, useEffect } from 'react';
import api from '../services/api';

function FormularioTransacao({ onTransacaoAdicionada }) {
    const [categorias, setCategorias] = useState([]);
    const [descricao, setDescricao] = useState('');
    const [valor, setValor] = useState('');
    const [data, setData] = useState('');
    const [tipo, setTipo] = useState('DESPESA'); // Valor padrão
    const [categoriaId, setCategoriaId] = useState('');
    const [error, setError] = useState('');

    // A MUDANÇA CRÍTICA ESTÁ AQUI
    // Este useEffect agora "assiste" à variável 'tipo'.
    // Toda vez que o valor de 'tipo' mudar, a função dentro do useEffect será executada novamente.
    useEffect(() => {
        const fetchCategorias = async () => {
            if (!tipo) return; // Não faz nada se o tipo não estiver selecionado

            setError(''); // Limpa erros de categoria
            try {
                // A URL agora é dinâmica e inclui o tipo selecionado
                const response = await api.get(`/categorias?tipo=${tipo}`);
                setCategorias(response.data);

                // Se a lista de categorias retornada não for vazia, define a primeira como padrão
                if (response.data.length > 0) {
                    setCategoriaId(response.data[0].id);
                } else {
                    // Se não houver categorias para o tipo, limpa a seleção
                    setCategoriaId('');
                }
            } catch (err) {
                console.error("Erro ao buscar categorias:", err);
                setError("Não foi possível carregar as categorias para este tipo.");
                setCategorias([]); // Limpa a lista de categorias em caso de erro
            }
        };

        fetchCategorias();
    }, [tipo]); // A variável [tipo] no final é a "dependência" que o useEffect observa.

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');

        if (!categoriaId) {
            setError("Por favor, selecione uma categoria.");
            return;
        }

        const dadosTransacao = {
            descricao,
            valor: parseFloat(valor),
            data,
            tipo,
            categoriaId: parseInt(categoriaId)
        };

        try {
            await api.post('/transacoes', dadosTransacao);
            onTransacaoAdicionada(); // Notifica o componente pai
            // Limpa o formulário após o envio
            setDescricao('');
            setValor('');
            setData('');
        } catch (err) {
            console.error("Erro ao cadastrar transação:", err);
            setError("Falha ao cadastrar transação. Verifique os dados.");
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h3>Cadastrar Nova Transação</h3>
            <div>
                <label>Tipo:</label>
                {/* Quando este select muda, o estado 'tipo' é atualizado, o que dispara o useEffect */}
                <select value={tipo} onChange={e => setTipo(e.target.value)}>
                    <option value="DESPESA">Despesa</option>
                    <option value="RECEITA">Receita</option>
                </select>
            </div>
            <div>
                <label>Categoria:</label>
                <select value={categoriaId} onChange={e => setCategoriaId(e.target.value)} required>
                    <option value="" disabled>
                        {categorias.length === 0 ? 'Nenhuma categoria encontrada' : 'Selecione...'}
                    </option>
                    {categorias.map(categoria => (
                        <option key={categoria.id} value={categoria.id}>
                            {categoria.nome}
                        </option>
                    ))}
                </select>
            </div>
            <div>
                <label>Descrição:</label>
                <input type="text" value={descricao} onChange={e => setDescricao(e.target.value)} required />
            </div>
            <div>
                <label>Valor (R$):</label>
                <input type="number" step="0.01" value={valor} onChange={e => setValor(e.target.value)} required />
            </div>
            <div>
                <label>Data:</label>
                <input type="date" value={data} onChange={e => setData(e.target.value)} required />
            </div>

            {error && <p style={{ color: 'red' }}>{error}</p>}
            <button type="submit">Adicionar</button>
        </form>
    );
}

export default FormularioTransacao;