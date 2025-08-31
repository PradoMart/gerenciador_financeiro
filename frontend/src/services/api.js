import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080', // url base da API no backend spring
});

export default api; // Exporta a instância do axios para ser usada em outros arquivos