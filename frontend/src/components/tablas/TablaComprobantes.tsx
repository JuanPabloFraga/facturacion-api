import React, { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { obtenerComprobantesFiltrados } from '../../api/facturacionApi';

const TablaComprobantes: React.FC = () => {
  const [tipoOperacion, setTipoOperacion] = useState<'COMPRA' | 'VENTA' | undefined>(undefined);
  const [fechaDesde, setFechaDesde] = useState<string>('');
  const [fechaHasta, setFechaHasta] = useState<string>('');

  const { data, isLoading, error, refetch } = useQuery({
    queryKey: ['comprobantes', tipoOperacion, fechaDesde, fechaHasta],
    queryFn: () => obtenerComprobantesFiltrados(
      tipoOperacion,
      undefined,
      fechaDesde === '' ? undefined : fechaDesde,
      fechaHasta === '' ? undefined : fechaHasta
    ),
  });

  const handleCheckBoxChange = (operacion: 'COMPRA' | 'VENTA') => {
    setTipoOperacion((prev) => (prev === operacion ? undefined : operacion));
  };

  return (
    <div>
      <h3>📌 Tabla de Comprobantes</h3>

      <div style={{ marginBottom: 20 }}>
        Desde:
        <input type="date" value={fechaDesde} onChange={(e) => setFechaDesde(e.target.value)} />
        Hasta:
        <input type="date" value={fechaHasta} onChange={(e) => setFechaHasta(e.target.value)} />
      </div>

      <div style={{ marginBottom: 20 }}>
        <label>
          <input
            type="checkbox"
            checked={tipoOperacion === 'COMPRA'}
            onChange={() => handleCheckBoxChange('COMPRA')}
          />
          Compra
        </label>
        <label style={{ marginLeft: 15 }}>
          <input
            type="checkbox"
            checked={tipoOperacion === 'VENTA'}
            onChange={() => handleCheckBoxChange('VENTA')}
          />
          Venta
        </label>
      </div>

      <button onClick={() => refetch()}>🔍 Aplicar filtros</button>

      {isLoading && <p>Cargando comprobantes...</p>}
      {error && <p>Error: {error.message}</p>}

      <table>
        <thead>
          <tr>
            <th>Tipo Comprobante</th>
            <th>Fecha</th>
            <th>Tipo Operación</th>
            <th>Número</th>
            <th>Cliente/Proveedor</th>
            <th>IVA</th>
            <th>Total</th>
          </tr>
        </thead>
        <tbody>
          {data?.length ? (
            data.map((comp) => (
              <tr key={comp.id}>
                <td>{comp.tipoComprobante}</td>
                <td>{comp.fecha}</td>
                <td>{comp.tipoOperacion}</td>
                <td>{comp.numero}</td>
                <td>{comp.clienteProveedor}</td>
                <td>{comp.montoIva}</td>
                <td>{comp.montoTotal}</td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan={7}>No hay comprobantes según los filtros seleccionados.</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default TablaComprobantes;