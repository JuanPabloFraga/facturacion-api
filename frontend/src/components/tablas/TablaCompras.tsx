import React from 'react';
import { useQuery } from '@tanstack/react-query';
import { obtenerComprobantesPorTipo } from '../../api/facturacionApi';

const TablaCompras = () => {
  const { data, isLoading, error } = useQuery({
    queryKey: ['compras'],
    queryFn: () => obtenerComprobantesPorTipo('compra'),
  });

  if (isLoading) return <span>Cargando compras...</span>;
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
        {data.map((compra: any) => (
          <tr key={compra.id}>
            <td>{compra.numero}</td>
            <td>{compra.letra}</td>
            <td>{compra.neto}</td>
            <td>{compra.iva}</td>
            <td>{compra.total}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default TablaCompras;