import React, { useState, useEffect, useCallback } from 'react';
import api from '../services/api';
import FormularioTransacao from '../components/FormularioTransacao';
import ListaTransacoes from '../components/ListaTransacoes';

function DashboardPage() {
    // O estado da lista de transações agora vive aqui, no componente "pai".
    const [transacoes, setTransacoes] = useState([]);
    const [error, setError] = useState('');

    // Usamos useCallback para evitar que a função seja recriada a cada renderização.
    const fetchTransacoes = useCallback(async () => {
        try {
            const response = await api.get('/transacoes');
            setTransacoes(response.data);
        } catch (err) {
            console.error("Erro ao buscar transações:", err);
            setError('Não foi possível carregar as transações.');
        }
    }, []);

    // useEffect para buscar as transações quando a página carrega.
    useEffect(() => {
        fetchTransacoes();
    }, [fetchTransacoes]);

    return (
        <div>
            <h1>Bem-vindo ao seu Dashboard Financeiro!</h1>
            <hr />
            {/* Passamos a função de recarregar como propriedade para o formulário */}
            <FormularioTransacao onTransacaoAdicionada={fetchTransacoes} />
            <hr />
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {/* Passamos a lista de transações como propriedade para a lista */}
            <ListaTransacoes transacoes={transacoes} />
        </div>
    );
}

export default DashboardPage;