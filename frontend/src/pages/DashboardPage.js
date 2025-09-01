import React, { useState, useEffect, useCallback } from 'react';
import api from '../services/api';
import FormularioTransacao from '../components/FormularioTransacao';
import ListaTransacoes from '../components/ListaTransacoes';
import ModalEdicao from '../components/ModalEdicao';

function DashboardPage() {
    // O estado da lista de transações agora vive aqui, no componente "pai".
    const [transacoes, setTransacoes] = useState([]);
    const [error, setError] = useState('');

    const [isModalOpen, setIsModalOpen] = useState(false); // Controla se o modal está visível
    const [transacaoSelecionada, setTransacaoSelecionada] = useState(null); // Guarda a transação a ser editada

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

    const handleExcluirTransacao = async (id) => {
        // Pede uma confirmação ao usuário antes de prosseguir
        if (window.confirm("Tem certeza que deseja excluir esta transação?")) {
            try {
                // Chama o endpoint DELETE da nossa API
                await api.delete(`/transacoes/${id}`);

                // ATUALIZA A LISTA NA TELA:
                // Cria uma nova lista contendo todas as transações, exceto a que tem o ID que acabamos de excluir.
                setTransacoes(transacoes.filter(transacao => transacao.id !== id));

                alert("Transação excluída com sucesso!");
            } catch (err) {
                console.error("Erro ao excluir transação:", err);
                setError('Não foi possível excluir a transação.');
            }
        }
    };

    const handleAbrirModalEdicao = (transacao) => {
        setTransacaoSelecionada(transacao); // Guarda a transação que foi clicada
        setIsModalOpen(true); // Abre o modal
    };

    const handleFecharModalEdicao = () => {
        setIsModalOpen(false); // Fecha o modal
        setTransacaoSelecionada(null); // Limpa a seleção
    };

    const handleSalvarEdicao = async (id, dadosAtualizados) => {
        try {
            // Chama o endpoint PUT da nossa API
            const response = await api.put(`/transacoes/${id}`, dadosAtualizados);

            // ATUALIZA A LISTA NA TELA:
            // Cria uma nova lista, substituindo a transação antiga pela nova (que veio da resposta da API)
            setTransacoes(transacoes.map(t => (t.id === id ? response.data : t)));

            alert("Transação atualizada com sucesso!");
            handleFecharModalEdicao(); // Fecha o modal
        } catch (err) {
            console.error("Erro ao atualizar transação:", err);
            // No futuro, podemos exibir este erro dentro do modal
            alert("Falha ao atualizar a transação.");
        }
    };

    return (
        <div>
            <h1>Bem-vindo ao seu Dashboard Financeiro!</h1>
            <hr />
            {/* Passamos a função de recarregar como propriedade para o formulário */}
            <FormularioTransacao onTransacaoAdicionada={fetchTransacoes} />
            <hr />
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {/* Passamos a lista de transações como propriedade para a lista */}
            <ListaTransacoes
                transacoes={transacoes}
                onExcluir={handleExcluirTransacao}
                onEditar={handleAbrirModalEdicao}
            />

            <ModalEdicao
                isOpen={isModalOpen}
                transacao={transacaoSelecionada}
                onClose={handleFecharModalEdicao}
                onSave={handleSalvarEdicao}
            />
        </div>
    );
}

export default DashboardPage;