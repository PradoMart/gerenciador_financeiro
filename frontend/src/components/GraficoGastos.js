import React from 'react';
import { PieChart, Pie, Cell, Tooltip, Legend, ResponsiveContainer } from 'recharts';

// O componente agora recebe os dados prontos via 'props'
function GraficoGastos({ data }) {

    const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042', '#AF19FF', '#FF19AF', '#19AFFF'];

    if (!data || data.length === 0) {
        return <p>Sem dados de despesas para exibir no gráfico.</p>;
    }

    return (
        <div style={{ width: '100%', height: 300 }}>
            <h3>Gastos por Categoria</h3>
            <ResponsiveContainer>
                <PieChart>
                    <Pie
                        data={data}
                        cx="50%"
                        cy="50%"
                        labelLine={false}
                        outerRadius={80}
                        fill="#8884d8"
                        dataKey="value"
                        nameKey="name"
                    >
                        {data.map((entry, index) => (
                            <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                        ))}
                    </Pie>
                    <Tooltip formatter={(value) => `R$ ${value.toFixed(2).replace('.',',')}`} />
                    <Legend />
                </PieChart>
            </ResponsiveContainer>
        </div>
    );
}

export default GraficoGastos;