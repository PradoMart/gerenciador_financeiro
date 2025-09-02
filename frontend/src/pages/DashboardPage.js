import React, { useState, useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
import FormularioTransacao from '../components/FormularioTransacao';
import ListaTransacoes from '../components/ListaTransacoes';
import ModalEdicao from '../components/ModalEdicao';
import GraficoGastos from '../components/GraficoGastos'; // 1. IMPORTE O NOVO COMPONENTE


function DashboardPage() {
    // O estado da lista de transações agora vive aqui, no componente "pai".
    const [transacoes, setTransacoes] = useState([]);
    const [error, setError] = useState('');

    const [isModalOpen, setIsModalOpen] = useState(false); // Controla se o modal está visível
    const [transacaoSelecionada, setTransacaoSelecionada] = useState(null); // Guarda a transação a ser editada

    const [dadosGrafico, setDadosGrafico] = useState([]);
    
    const navigate = useNavigate();

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

    const fetchDadosGrafico = useCallback(async () => {
        try {
            const response = await api.get('/dashboard/gastos-por-categoria');
            const formattedData = response.data.map(item => ({
                name: item.categoria,
                value: item.totalGasto
            }));
            setDadosGrafico(formattedData);
        } catch (err) {
            console.error("Erro ao buscar dados do gráfico:", err);
            // Não definimos um erro aqui para não poluir a tela, mas poderíamos
        }
    }, []);

    // useEffect para buscar as transações quando a página carrega.
    useEffect(() => {
        fetchTransacoes();
        fetchDadosGrafico();
    }, [fetchTransacoes, fetchDadosGrafico]);

    // Função "orquestradora" que é chamada após uma nova transação ser adicionada
    const handleTransacaoAdicionada = () => {
        // Recarrega tanto a lista quanto o gráfico
        fetchTransacoes();

        // Adicionamos um pequeno delay para dar tempo do backend processar
        // a nova transação antes de recalcular os totais do gráfico.
        setTimeout(fetchDadosGrafico, 500);
    };

    const handleExcluirTransacao = async (id) => {
        // Pede uma confirmação ao usuário antes de prosseguir
        if (window.confirm("Tem certeza que deseja excluir esta transação?")) {
            try {
                // Chama o endpoint DELETE da nossa API
                await api.delete(`/transacoes/${id}`);

                // ATUALIZA A LISTA NA TELA:
                // Cria uma nova lista contendo todas as transações, exceto a que tem o ID que acabamos de excluir.
                setTransacoes(transacoes.filter(transacao => transacao.id !== id));
                setTimeout(fetchDadosGrafico, 500);

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
            const response = await api.put(`/transacoes/${id}`, dadosAtualizados);
            setTransacoes(transacoes.map(t => (t.id === id ? response.data : t)));

            // Também atualiza o gráfico após editar
            setTimeout(fetchDadosGrafico, 500);

            alert("Transação atualizada com sucesso!");
            handleFecharModalEdicao();
        } catch (err) {
            console.error("Erro ao atualizar transação:", err);
            alert("Falha ao atualizar a transação.");
        }
    };

    const handleLogout = () => {
        // Remove o token do armazenamento local do navegador
        localStorage.removeItem('authToken');

        // Remove o cabeçalho de autorização das futuras requisições do Axios
        delete api.defaults.headers.common['Authorization'];

        // Redireciona o usuário para a página de login
        navigate('/login');
    };

    return (
        <div>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <h1>Bem-vindo ao seu Dashboard Financeiro!</h1>
                <button onClick={handleLogout} style={{ padding: '8px 16px' }}>Sair</button>
            </div>
            <hr />
            {/* O gráfico agora recebe os dados do estado desta página */}
            <GraficoGastos data={dadosGrafico} />
            <hr />
            
            {/* O formulário agora chama a função "orquestradora" */}
            <FormularioTransacao onTransacaoAdicionada={handleTransacaoAdicionada} />
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