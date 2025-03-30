import React from 'react';
import Modal from 'react-modal';
import { useForm, SubmitHandler } from 'react-hook-form';
import { CreateClienteProveedorDTO, TipoResponsable } from '../../api/type';
import { crearClienteProveedor } from '../../api/facturacionApi';
import { useQueryClient } from '@tanstack/react-query';

interface Props {
  isOpen: boolean;
  onClose: () => void;
}

const NuevoClienteModal: React.FC<Props> = ({ isOpen, onClose }) => {
  const { register, handleSubmit, formState: { errors }, reset } = useForm<CreateClienteProveedorDTO>();
  const queryClient = useQueryClient();

  const onSubmit: SubmitHandler<CreateClienteProveedorDTO> = async (clienteData) => {
    try {
      await crearClienteProveedor(clienteData);
      queryClient.invalidateQueries({ queryKey: ["clientesProveedores"] });
      reset();
      onClose();
    } catch (error) {
      console.error('Error al crear cliente:', error);
    }
  };

  return (
    <Modal isOpen={isOpen} onRequestClose={onClose}>
      <h2>Nuevo Cliente</h2>

      <form onSubmit={handleSubmit(onSubmit)}>
        <input {...register('razonSocial', { required: "Razón social obligatoria" })}
          placeholder="Razón Social" />
        <span>{errors.razonSocial?.message}</span>

        <input {...register('cuit', { required: "CUIT obligatorio" })}
          placeholder="CUIT" />
        <span>{errors.cuit?.message}</span>

        <input {...register('direccion')} placeholder="Dirección" />
        <input {...register('telefono')} placeholder="Teléfono" />
        <input {...register('email')} placeholder="Email" />


        <select {...register('tipoResponsable', { required: "Tipo responsable obligatorio"})}>
          <option value="">Selecciona tipo responsable</option>
          {Object.values(TipoResponsable).map((tipo) => (
            <option key={tipo} value={tipo}>
              {tipo.replace('_', ' ')}
            </option>
          ))}
        </select>
        <span>{errors.tipoResponsable?.message}</span>

        <button type="submit">Crear Cliente</button>
        <button type="button" onClick={onClose}>Cancelar</button>
      </form>
    </Modal>
  );
};

export default NuevoClienteModal;