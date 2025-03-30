import { useQuery } from '@tanstack/react-query';
import { obtenerClientesProveedores } from '../api/facturacionApi';

export const useClientesProveedores = () => {
  return useQuery({
    queryKey: ['clientesProveedores'],
    queryFn: obtenerClientesProveedores,
  });
};