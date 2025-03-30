import React from 'react';
import { useQuery } from '@tanstack/react-query';
import { obtenerTodosComprobantes } from '../../api/facturacionApi';

const TablaComprobantes = () => {
  const { data, isLoading, error } = useQuery({
    queryKey: ['comprobantes'],
    queryFn: obtenerTodosComprobantes,
  });

  if (isLoading) return <span>Cargando comprobantes...</span>;
  if (error) return <span>Error: {error.message}</span>;

  return (
    <table>
      <thead>
        <tr>
          <th>Número</th>
          <th>Letra</th>
          <th>Neto</th>
          <th>IVA</th>
          <th>Total</th>
        </tr>
      </thead>
      <tbody>
        {data.map((comp: any) => (
          <tr key={comp.id}>
            <td>{comp.numero}</td>
            <td>{comp.letra}</td>
            <td>{comp.neto}</td>
            <td>{comp.iva}</td>
            <td>{comp.total}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default TablaComprobantes;