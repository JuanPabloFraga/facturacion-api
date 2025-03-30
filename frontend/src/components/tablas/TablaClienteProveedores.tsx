import React from 'react';
import { useClientesProveedores } from '../../Queries/useClientesProveedores';

const TablaClientesProveedores = () => {
  const { data: clientes, isLoading, error } = useClientesProveedores();

  if (isLoading) return <span>Cargando clientes/proveedores...</span>;
  if (error) return <span>Error: {error.message}</span>;

  return (
    <table>
      <thead>
        <tr>
          <th>Razon Social</th>
          <th>Tipo</th>
          <th>CUIT</th>
          <th>Tipo Responsable</th>
        </tr>
      </thead>
      <tbody>
        {clientes?.map((cp) => (
          <tr key={cp.id}>
            <td>{cp.razonSocial}</td>
            <td>{cp.tipo}</td>
            <td>{cp.cuit}</td>
            <td>{cp.tipoResponsable}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default TablaClientesProveedores;