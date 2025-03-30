import React from 'react';
import { NavLink } from 'react-router-dom';

const Sidebar = () => {
  return (
    <nav style={{ padding: '20px', width: '250px', height: '100vh', backgroundColor: 'blue' }}>
      <h3>Menú</h3>
      <ul style={{ listStyle: 'none', padding: 0 }}>

        <li><NavLink to="/clientes-proveedores">👥Clientes/Proveedores</NavLink></li>
        <li><NavLink to="/comprobantes">📄 Comprobantes</NavLink></li>
        <li><NavLink to="/ventas">💰 Ventas</NavLink></li>
        <li><NavLink to="/compras">🛒 Compras</NavLink></li>
      </ul>
    </nav>
  );
};

export default Sidebar;