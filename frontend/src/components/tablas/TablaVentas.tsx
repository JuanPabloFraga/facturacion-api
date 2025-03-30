import React from 'react';
import { useQuery } from '@tanstack/react-query';
import { obtenerComprobantesPorTipo } from '../../api/facturacionApi';

const TablaVentas = () => {
  const { data, isLoading, error } = useQuery({
    queryKey: ['ventas'],
    queryFn: () => obtenerComprobantesPorTipo('venta'),
  });

  if (isLoading) return <span>Cargando ventas...</span>;
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
        {data.map((venta: any) => (
          <tr key={venta.id}>
            <td>{venta.numero}</td>
            <td>{venta.letra}</td>
            <td>{venta.neto}</td>
            <td>{venta.iva}</td>
            <td>{venta.total}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default TablaVentas;