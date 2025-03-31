import React from 'react';
import { useClientesProveedores } from '../../Queries/useClientesProveedores';
import 'bootstrap/dist/css/bootstrap.min.css';
const TablaClientesProveedores = () => {
  const { data: clientes, isLoading, error } = useClientesProveedores();

  if (isLoading) return <span>Cargando clientes/proveedores...</span>;
  if (error) return <span>Error: {error.message}</span>;

  return (
    <table className="table table-bordered table-dark " >
      <thead className = "table-light">
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