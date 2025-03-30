// routes/MiRutasApp.tsx
import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Layout from '../components/layout/Layout';
import HomePage from '../pages/HomePage';
import ClientesProveedoresPage from '../pages/ClientesProveedoresPage';
import ComprobantesPage from '../pages/ComprobantesPage';
import VentasPage from '../pages/VentasPage';
import ComprasPage from '../pages/ComprasPage';

const MiRutasApp = () => (
  <BrowserRouter>
    <Layout>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/clientes-proveedores" element={<ClientesProveedoresPage />} />
        <Route path="/comprobantes" element={<ComprobantesPage />} />
        <Route path="/ventas" element={<VentasPage />} />
        <Route path="/compras" element={<ComprasPage />} />
      </Routes>
    </Layout>
  </BrowserRouter>
);

export default MiRutasApp;