import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import DashboardPage from './pages/DashboardPage';


function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to ="/login" />} /> {/* Redireciona a rota raiz para /login */}
        <Route path="/login" element={<LoginPage />} /> {/* Rota para a página de login */}
        <Route path="/register" element={<RegisterPage />} /> {/* Rota para a página de registro */}
        <Route path="/dashboard" element={<DashboardPage />} /> {/* Rota para a página do dashboard */}
      </Routes>
    </BrowserRouter>
  );
}

export default App;
