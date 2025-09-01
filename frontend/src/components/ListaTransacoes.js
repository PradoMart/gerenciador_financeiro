import React from 'react';

// Este componente recebe a lista de transações como uma "prop" (propriedade)
function ListaTransacoes({ transacoes, onExcluir, onEditar }) {

    // Função para formatar a data para o padrão brasileiro
    const formatarData = (data) => {
        // Adicionamos um dia à data para corrigir problemas de fuso horário (UTC)
        const dataObj = new Date(data);
        dataObj.setDate(dataObj.getDate() + 1);
        return dataObj.toLocaleDateString('pt-BR');
    };

    // Função para formatar o valor como moeda brasileira
    const formatarValor = (valor) => {
        return valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
    };

    return (
        <div>
            <h3>Suas Transações</h3>
            <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                <thead> {/* Estrutura de Cabeçalho da tabela */ }
                    <tr> 
                        <th style={{ border: '1px solid #ddd', padding: '8px' }}>Data</th>
                        <th style={{ border: '1px solid #ddd', padding: '8px' }}>Descrição</th>
                        <th style={{ border: '1px solid #ddd', padding: '8px' }}>Categoria</th>
                        <th style={{ border: '1px solid #ddd', padding: '8px' }}>Valor</th>

                        <th style={{ border: '1px solid #ddd', padding: '8px' }}>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    {/* Se não houver transações, mostramos uma mensagem */}
                    {transacoes.length === 0 ? (
                        <tr>
                            <td colSpan="4" style={{ textAlign: 'center', padding: '10px' }}>Nenhuma transação encontrada.</td>
                        </tr>
                    ) : (
                        // Usamos .map() para criar uma linha <tr> para cada transação
                        transacoes.map(transacao => (
                            <tr key={transacao.id}>
                                <td style={{ border: '1px solid #ddd', padding: '8px' }}>{formatarData(transacao.data)}</td>
                                <td style={{ border: '1px solid #ddd', padding: '8px' }}>{transacao.descricao}</td>
                                <td style={{ border: '1px solid #ddd', padding: '8px' }}>{transacao.categoria.nome}</td>
                                {/* Mudamos a cor do texto com base no tipo da transação */}
                                <td style={{ border: '1px solid #ddd', padding: '8px', color: transacao.tipo === 'RECEITA' ? 'green' : 'red' }}>
                                    {formatarValor(transacao.valor)}
                                </td>
                                <td style={{ border: '1px solid #ddd', padding: '8px', textAlign: 'center' }}>
                                    <button onClick={() => onEditar(transacao)} style={{ marginRight: '5px' }}>Editar</button>
                                    <button onClick={() => onExcluir(transacao.id)}>Excluir</button>
                                </td>
                            </tr>
                        ))
                    )}
                </tbody>
            </table>
        </div>
    );
}

export default ListaTransacoes;