import React, { useEffect, useState } from 'react';
import Modal from 'react-modal';
import axios from 'axios';
import { ClienteProveedor } from '../../types/type';

interface Props {
  isOpen: boolean;
  onClose: () => void;
  onSelectCliente: (cliente: ClienteProveedor) => void;
}

const BuscarClienteModal: React.FC<Props> = ({ isOpen, onClose, onSelectCliente }) => {
  const [clientes, setClientes] = useState<ClienteProveedor[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchClientes = async () => {
      try {
        const response = await axios.get<ClienteProveedor[]>('/api/clientes');
        setClientes(response.data);
        setLoading(false);
      } catch (error) {
        console.error("Error obteniendo clientes:", error);
        setLoading(false);
      }
    };

    fetchClientes();
  }, []);

  return (
    <Modal isOpen={isOpen} onRequestClose={onClose}>
      <h2>Seleccionar Cliente</h2>
      {loading ? (
        <p>Cargando...</p>
      ) : (
        <ul>
          {clientes.map(cliente => (
            <li key={cliente.id} onClick={() => {
              onSelectCliente(cliente);
              onClose();
            }}>
              {cliente.razonSocial} - CUIT: {cliente.cuit}
            </li>
          ))}
        </ul>
      )}

      <button onClick={onClose}>Cerrar</button>
    </Modal>
  );
};

export default BuscarClienteModal;