import React, { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { obtenerComprobantesFiltrados } from '../../api/facturacionApi';
import 'bootstrap/dist/css/bootstrap.min.css';

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

  // Calculate totals
  const totals = data?.reduce((acc, comp) => ({
    subtotal: acc.subtotal + (comp.subtotal || 0),
    montoIva: acc.montoIva + (comp.montoIva || 0),
    montoTotal: acc.montoTotal + (comp.montoTotal || 0)
  }), { subtotal: 0, montoIva: 0, montoTotal: 0 });

  return (
    <div className="container mt-4">
      <div className="card shadow">
        <div className="card-header bg-primary text-white">
          <h3 className="mb-0">📌 Tabla de Comprobantes</h3>
        </div>

        <div className="card-body">
         
          <div className="row mb-3 g-3">
            <div className="col-md-3">
              <label className="form-label">Desde:</label>
              <input
                type="date"
                className="form-control"
                value={fechaDesde}
                onChange={(e) => setFechaDesde(e.target.value)}
              />
            </div>
            <div className="col-md-3">
              <label className="form-label">Hasta:</label>
              <input
                type="date"
                className="form-control"
                value={fechaHasta}
                onChange={(e) => setFechaHasta(e.target.value)}
              />
            </div>
            <div className="col-md-4 d-flex align-items-end">
              <div className="form-check form-check-inline">
                <input
                  className="form-check-input"
                  type="checkbox"
                  checked={tipoOperacion === 'COMPRA'}
                  onChange={() => handleCheckBoxChange('COMPRA')}
                />
                <label className="form-check-label">Compra</label>
              </div>
              <div className="form-check form-check-inline">
                <input
                  className="form-check-input"
                  type="checkbox"
                  checked={tipoOperacion === 'VENTA'}
                  onChange={() => handleCheckBoxChange('VENTA')}
                />
                <label className="form-check-label">Venta</label>
              </div>
            </div>
            <div className="col-md-2 d-flex align-items-end">
              <button 
                className="btn btn-primary w-100"
                onClick={() => refetch()}
              >
                🔍 Aplicar filtros
              </button>
            </div>
          </div>

       
          {isLoading && <div className="alert alert-info">Cargando comprobantes...</div>}
          {error && <div className="alert alert-danger">Error: {error.message}</div>}

          
          <div className="table-responsive">
            <table className="table table-bordered table-striped table-hover">
              <thead className="table-dark">
                <tr>
                  <th>Tipo Comprobante</th>
                  <th>Fecha</th>
                  <th>Tipo Operación</th>
                  <th>Número</th>
                  <th>Cliente/Proveedor</th>
                  <th className="text-end">IVA</th>
                  <th className="text-end">Subtotal</th>
                  <th className="text-end">Total</th>
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
                      <td className="text-end">${comp.montoIva?.toLocaleString('es-AR', {minimumFractionDigits: 2})}</td>
                      <td className="text-end">${comp.subtotal?.toLocaleString('es-AR', {minimumFractionDigits: 2})}</td>
                      <td className="text-end">${comp.montoTotal?.toLocaleString('es-AR', {minimumFractionDigits: 2})}</td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan={8} className="text-center text-muted py-4">
                      No hay comprobantes según los filtros seleccionados
                    </td>
                  </tr>
                )}
              </tbody>
              {data?.length && totals && (
                <tfoot>
                  <tr className="table-active">
                    <td colSpan={5} className="text-end fw-bold">Totales:</td>
                    <td className="text-end fw-bold">${totals.montoIva.toLocaleString('es-AR', {minimumFractionDigits: 2})}</td>
                    <td className="text-end fw-bold">${totals.subtotal.toLocaleString('es-AR', {minimumFractionDigits: 2})}</td>
                    <td className="text-end fw-bold">${totals.montoTotal.toLocaleString('es-AR', {minimumFractionDigits: 2})}</td>
                  </tr>
                </tfoot>
              )}
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TablaComprobantes;