import axios from 'axios';

// Configuración base claramente definida para Axios
const apiClient = axios.create({
  baseURL: 'http://localhost:8081/api', // aquí claramente tu backend real
  headers: {
    'Content-Type': 'application/json',
  },
});

export default apiClient;