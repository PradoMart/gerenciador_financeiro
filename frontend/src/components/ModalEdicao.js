import React, { useState, useEffect } from 'react';
import api from '../services/api';

function ModalEdicao({ transacao, onClose, onSave, isOpen }) {
    // Estados para controlar os campos do formulário
    const [formData, setFormData] = useState({});
    const [categorias, setCategorias] = useState([]);
    const [error, setError] = useState('');

    // Este useEffect "assiste" à transação selecionada.
    // Quando uma nova transação é passada para o modal, ele atualiza o estado do formulário.
    useEffect(() => {
        if (transacao) {
            // Formata a data para o formato YYYY-MM-DD que o input[type=date] espera
            const dataFormatada = new Date(transacao.data).toISOString().split('T')[0];
            setFormData({ ...transacao, data: dataFormatada, categoriaId: transacao.categoria.id });
        }
    }, [transacao]);

    // Este useEffect busca as categorias corretas (RECEITA/DESPESA) sempre que o tipo da transação muda.
    useEffect(() => {
        const fetchCategorias = async () => {
            if (!formData.tipo) return;
            try {
                const response = await api.get(`/categorias?tipo=${formData.tipo}`);
                setCategorias(response.data);
            } catch (err) {
                console.error("Erro ao buscar categorias:", err);
                setError("Não foi possível carregar as categorias.");
            }
        };
        fetchCategorias();
    }, [formData.tipo]);


    // Função para atualizar o estado do formulário quando um campo muda
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevState => ({ ...prevState, [name]: value }));
    };

    // Função para lidar com o salvamento
    const handleSubmit = (e) => {
        e.preventDefault();
        const dadosAtualizados = {
            descricao: formData.descricao,
            valor: parseFloat(formData.valor),
            data: formData.data,
            tipo: formData.tipo,
            categoriaId: parseInt(formData.categoriaId),
        };
        onSave(formData.id, dadosAtualizados); // Chama a função de salvar do Dashboard
    };

    if (!isOpen || !transacao) return null;

    const modalStyle = {
            position: 'fixed',
            top: '50%',
            left: '50%',
            transform: 'translate(-50%, -50%)',
            backgroundColor: 'white',
            padding: '20px',
            zIndex: 1000,
            border: '1px solid #ccc',
            borderRadius: '8px',
            boxShadow: '0 4px 8px rgba(0,0,0,0.1)'
        };

        const overlayStyle = {
            position: 'fixed',
            top: 0,
            left: 0,
            right: 0,
            bottom: 0,
            backgroundColor: 'rgba(0,0,0,0.5)',
            zIndex: 999
        };
    
    return (
        <div style={overlayStyle}>
            <div style={modalStyle}>
                <h2>Editar Transação</h2>
                <form onSubmit={handleSubmit}>
                    {/* Formulário preenchido com os dados da transação */}
                    <div>
                        <label>Descrição:</label>
                        <input type="text" name="descricao" value={formData.descricao || ''} onChange={handleChange} required />
                    </div>
                    <div>
                        <label>Valor (R$):</label>
                        <input type="number" step="0.01" name="valor" value={formData.valor || ''} onChange={handleChange} required />
                    </div>
                    <div>
                        <label>Data:</label>
                        <input type="date" name="data" value={formData.data || ''} onChange={handleChange} required />
                    </div>
                    <div>
                        <label>Tipo:</label>
                        <select name="tipo" value={formData.tipo || ''} onChange={handleChange}>
                            <option value="DESPESA">Despesa</option>
                            <option value="RECEITA">Receita</option>
                        </select>
                    </div>
                    <div>
                        <label>Categoria:</label>
                        <select name="categoriaId" value={formData.categoriaId || ''} onChange={handleChange} required>
                            {categorias.map(categoria => (
                                <option key={categoria.id} value={categoria.id}>
                                    {categoria.nome}
                                </option>
                            ))}
                        </select>
                    </div>
                    {error && <p style={{ color: 'red' }}>{error}</p>}

                    {/* Botões do formulário */}
                    <div style={{ marginTop: '20px' }}>
                        <button type="button" onClick={onClose} style={{ marginRight: '10px' }}>Cancelar</button>
                        <button type="submit">Salvar Alterações</button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default ModalEdicao;