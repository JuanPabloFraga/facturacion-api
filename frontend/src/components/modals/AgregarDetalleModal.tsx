import React from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import Modal from 'react-modal';
import { CreateDetalleComprobanteDTO } from '../../types/type';

interface Props {
  isOpen: boolean;
  onClose: () => void;
  onAgregarDetalle: (detalle: CreateDetalleComprobanteDTO) => void;
}

const AgregarDetalleModal: React.FC<Props> = ({ isOpen, onClose, onAgregarDetalle }) => {
  const { register, handleSubmit, formState: { errors }, reset } = useForm<CreateDetalleComprobanteDTO>();

  const onSubmit: SubmitHandler<CreateDetalleComprobanteDTO> = (data) => {
    onAgregarDetalle(data);
    reset();
    onClose();
  };

  return (
    <Modal isOpen={isOpen} onRequestClose={onClose}>
      <h2>Agregar Detalle 📝</h2>

      <form onSubmit={handleSubmit(onSubmit)}>
        <input {...register('descripcion', { required: 'Campo obligatorio' })}
               placeholder="Descripción del detalle" />
        <span>{errors.descripcion?.message}</span>

        <input type="number" step="any" {...register('cantidad', { required: 'Cantidad obligatoria', valueAsNumber: true })}
               placeholder="Cantidad" />
        <span>{errors.cantidad?.message}</span>

        <input type="number" step="any" {...register('precioUnitario', { required: 'Precio Unitario obligatorio', valueAsNumber: true })}
               placeholder="Precio Unitario" />
        <span>{errors.precioUnitario?.message}</span>

        <input type="number" step="any" {...register('alicuotaIva', { required: 'IVA obligatorio', valueAsNumber: true })}
               placeholder="Alicuota IVA (%)" />
        <span>{errors.alicuotaIva?.message}</span>

        <button type="submit"> ➕ Agregar Detalle</button>
        <button type="button" onClick={onClose}>Cancelar</button>
      </form>
    </Modal>
  );
};

export default AgregarDetalleModal;