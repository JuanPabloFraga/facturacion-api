import { useState } from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { CreateFacturaDTO, CreateDetalleComprobanteDTO } from '../types/type';
import axios from 'axios';

import BuscarClienteModal from './modals/BuscarClienteModal';
import NuevoClienteModal from './modals/NuevoClienteModal';
import AgregarDetalleModal from './modals/AgregarDetalleModal';

const CrearFacturaComponent = () => {

  // Estados claramente definidos para modales y manejo cliente/detalles
  const [buscarClienteOpen, setBuscarClienteOpen] = useState(false);
  const [nuevoClienteOpen, setNuevoClienteOpen] = useState(false);
  const [modalDetalleOpen, setModalDetalleOpen] = useState(false);

  const [clienteSeleccionado, setClienteSeleccionado] = useState<number | null>(null);
  const [detalles, setDetalles] = useState<CreateDetalleComprobanteDTO[]>([]);

  const { register, handleSubmit, formState: { errors }, reset } = useForm<CreateFacturaDTO>();

  const onSubmit: SubmitHandler<CreateFacturaDTO> = async (data) => {
    if (!clienteSeleccionado) {
      alert("Debes seleccionar un cliente claramente antes de enviar.");
      return;
    }

    if (detalles.length === 0) {
      alert("Agrega al menos 1 detalle antes de enviar claramente.");
      return;
    }

    const facturaCompleta: CreateFacturaDTO = {
      ...data,
      clienteProveedor: clienteSeleccionado,
      detalles,
    };

    try {
      await axios.post('/api/facturas', facturaCompleta);
      alert('Factura creada exitosamente 🚀');
      reset();
      setDetalles([]);
      setClienteSeleccionado(null);
    } catch (error) {
      alert('Error al crear la factura: revisa la consola claramente.');
      console.error(error);
    }
  };

  return (
    <div>
      <h1>Crear Factura 📝</h1>

      <form onSubmit={handleSubmit(onSubmit)}>
        <div>
          <label>Número Factura:</label>
          <input {...register('numero', { required: "Número obligatorio" })} />
          <span>{errors.numero?.message}</span>
        </div>

        <div>
          <label>Fecha:</label>
          <input type="date" {...register('fecha', { required: "Fecha obligatoria" })} />
          <span>{errors.fecha?.message}</span>
        </div>

        <div>
          <label>Tipo Operación:</label>
          <select {...register('tipoOperacion', { required: "Campo tipo operación obligatorio" })}>
            <option value="">--Selecciona una opción--</option>
            <option value="COMPRA">Compra</option>
            <option value="VENTA">Venta</option>
          </select>
          <span>{errors.tipoOperacion?.message}</span>
        </div>

        <div>
          <label>Letra Factura:</label>
          <input {...register('letraFactura', { required: "Letra obligatoria" })} />
          <span>{errors.letraFactura?.message}</span>
        </div>

        <div>
          <label>Punto de Venta:</label>
          <input type="number" {...register('puntoVenta', { required: "Punto de Venta obligatorio", valueAsNumber: true })} />
          <span>{errors.puntoVenta?.message}</span>
        </div>

        <div>
          <label>Cliente seleccionado ID: {clienteSeleccionado || 'Ninguno'}</label>
          <button type="button" onClick={() => setBuscarClienteOpen(true)}>🔎 Buscar Cliente</button>
          <button type="button" onClick={() => setNuevoClienteOpen(true)}>➕ Nuevo Cliente</button>
        </div>

        <div>
          <label>Detalles ({detalles.length} agregados):</label>
          <button type="button" onClick={() => setModalDetalleOpen(true)}>➕ Agregar Detalle</button>
          {detalles.map((detalle, index) => (
            <div key={index}>
              {detalle.descripcion} — {detalle.cantidad} x ${detalle.precioUnitario}
            </div>
          ))}
        </div>

        <button type="submit">Enviar Factura 🚀</button>
      </form>

      {/* Modales claramente integrados */}
      <BuscarClienteModal
        isOpen={buscarClienteOpen}
        onClose={() => setBuscarClienteOpen(false)}
        onSelectCliente={(cliente) => setClienteSeleccionado(cliente.id)}
      />

      <NuevoClienteModal
        isOpen={nuevoClienteOpen}
        onClose={() => setNuevoClienteOpen(false)}
        onClienteCreado={(nuevoCliente) => setClienteSeleccionado(nuevoCliente.id as number)}
      />

      <AgregarDetalleModal
        isOpen={modalDetalleOpen}
        onClose={() => setModalDetalleOpen(false)}
        onAgregarDetalle={(detalle) => setDetalles(prev => [...prev, detalle])}
      />
    </div>
  );
};

export default CrearFacturaComponent;