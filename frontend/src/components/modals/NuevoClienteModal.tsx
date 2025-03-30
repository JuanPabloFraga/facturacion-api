import React from 'react';
import Modal from 'react-modal';
import { useForm, SubmitHandler } from 'react-hook-form';
import axios from 'axios';
import { CreateClienteProveedorDTO } from '../../types/type';

interface Props {
  isOpen: boolean;
  onClose: () => void;
  onClienteCreado: (cliente: CreateClienteProveedorDTO) => void;
}

const NuevoClienteModal: React.FC<Props> = ({ isOpen, onClose, onClienteCreado }) => {
  const { register, handleSubmit, formState: { errors }, reset } = useForm<CreateClienteProveedorDTO>();

  const onSubmit: SubmitHandler<CreateClienteProveedorDTO> = async (clienteData) => {
    try {
      const response = await axios.post('/api/clientes', clienteData);
      onClienteCreado(response.data);
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
        <input {...register('tipoResponsable')} placeholder="Tipo Responsable" />

        <button type="submit">Crear Cliente</button>
        <button type="button" onClick={onClose}>Cancelar</button>
      </form>
    </Modal>
  );
};

export default NuevoClienteModal;