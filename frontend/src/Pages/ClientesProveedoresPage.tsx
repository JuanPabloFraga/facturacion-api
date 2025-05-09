// ClientesProveedoresPage.tsx
import React, { useState } from 'react';
import TablaClientesProveedores from '../components/tablas/TablaClienteProveedores';
import NuevoClienteModal from '../components/modals/NuevoClienteModal';
import BotonCrear from '../components/botones/BotonCrear';

const ClientesProveedoresPage = () => {
  const [modalAbierto, setModalAbierto] = useState(false);

  const manejarClienteCreado = () => {
    setModalAbierto(false);
    
  };

  return (
    <div>
      <h2>Clientes y Proveedores 👥</h2>

      <BotonCrear
        texto="Crear Cliente o Proveedor"
        onClick={() => setModalAbierto(true)} // Usas aquí la función personalizada
      />

      <NuevoClienteModal
        isOpen={modalAbierto}
        onClose={() => setModalAbierto(false)}
        onClienteCreado={manejarClienteCreado}
      />
      <p>
        </p>
      <TablaClientesProveedores />
    </div>
  );
};

export default ClientesProveedoresPage;