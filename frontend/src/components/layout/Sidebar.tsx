import React from 'react';
import { NavLink } from 'react-router-dom';

const Sidebar = () => {
  return (
    <nav className="app-sidebar">
      <h3>Menú Principal</h3>
      <ul>
        <li>
          <NavLink 
            to="/clientes-proveedores"
            className={({ isActive }) => isActive ? 'active' : ''}
          >
            👥 Clientes/Proveedores
          </NavLink>
        </li>
        <li><NavLink to="/comprobantes">📄 Comprobantes</NavLink></li>
        <li><NavLink to="/ventas">💰 Ventas</NavLink></li>
        <li><NavLink to="/compras">🛒 Compras</NavLink></li>
      </ul>
    </nav>
  );
};

export default Sidebar;