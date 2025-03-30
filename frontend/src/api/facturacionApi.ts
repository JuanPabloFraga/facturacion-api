import apiClient from './apiClient';
import {
  FacturaResponseDTO,
  CreateFacturaDTO,
  NotaDebitoResponseDTO,
  CreateNotaDebitoDTO,
  NotaCreditoResponseDTO,
  CreateNotaCreditoDTO,
  ClienteProveedorResponseDTO,
  CreateClienteProveedorDTO,
  ComprobanteResponseDTO
} from './types';

// >>>>>>>>>>> CLIENTES PROVEEDORES <<<<<<<<<<<< //

// Crear Cliente/Proveedor✅
export const crearClienteProveedor = async (datosCliente: CreateClienteProveedorDTO): Promise<ClienteProveedorResponseDTO> => {
  const response = await apiClient.post<ClienteProveedorResponseDTO>('/clientes-proveedores', datosCliente);
  return response.data;
};

// Obtener Cliente/Proveedor por ID✅
export const obtenerClienteProveedor = async (id: number): Promise<ClienteProveedorResponseDTO> => {
  const response = await apiClient.get<ClienteProveedorResponseDTO>(`/clientes-proveedores/${id}`);
  return response.data;
};

// Listar todos Clientes/Proveedores✅
export const obtenerClientesProveedores = async (): Promise<ClienteProveedorResponseDTO[]> => {
  const respuesta = await apiClient.get('/clientes-proveedores');
  return respuesta.data;
};


// >>>>>>>>>>>> FACTURAS <<<<<<<<<<<<<< //

// Crear una factura✅
export const crearFactura = async (factura: CreateFacturaDTO): Promise<FacturaResponseDTO> => {
  const response = await apiClient.post<FacturaResponseDTO>('/comprobantes/facturas', factura);
  return response.data;
};

// Obtener factura por ID✅
export const obtenerFactura = async (id: number): Promise<FacturaResponseDTO> => {
  const response = await apiClient.get<FacturaResponseDTO>(`/comprobantes/facturas/${id}`);
  return response.data;
};

// Obtener todas las facturas ✅ << NUEVO >>
export const obtenerTodasFacturas = async (): Promise<FacturaResponseDTO[]> => {
  const response = await apiClient.get('/comprobantes/facturas');
  return response.data;
};


// >>>>>>>>>>>> NOTAS DE CRÉDITO <<<<<<<<<<<<<< //

// Crear nota de crédito✅
export const crearNotaCredito = async (notaCredito: CreateNotaCreditoDTO): Promise<NotaCreditoResponseDTO> => {
  const response = await apiClient.post<NotaCreditoResponseDTO>('/comprobantes/notas-credito', notaCredito);
  return response.data;
};

// Obtener nota de crédito por ID✅
export const obtenerNotaCredito = async (id: number): Promise<NotaCreditoResponseDTO> => {
  const response = await apiClient.get<NotaCreditoResponseDTO>(`/comprobantes/notas-credito/${id}`);
  return response.data;
};

// Obtener todas las notas de crédito ✅ << NUEVO >>
export const obtenerTodasNotasCredito = async (): Promise<NotaCreditoResponseDTO[]> => {
  const response = await apiClient.get('/comprobantes/notas-credito');
  return response.data;
};


// >>>>>>>>>>> NOTAS DE DEBITO <<<<<<<<<<<<<< //

// Crear nota de débito✅
export const crearNotaDebito = async (notaDebito: CreateNotaDebitoDTO): Promise<NotaDebitoResponseDTO> => {
  const response = await apiClient.post<NotaDebitoResponseDTO>('/comprobantes/notas-debito', notaDebito);
  return response.data;
};

// Obtener nota de débito por ID✅
export const obtenerNotaDebito = async (id: number): Promise<NotaDebitoResponseDTO> => {
  const response = await apiClient.get<NotaDebitoResponseDTO>(`/comprobantes/notas-debito/${id}`);
  return response.data;
};

// Obtener todas las notas de débito ✅ << NUEVO >>
export const obtenerTodasNotasDebito = async (): Promise<NotaDebitoResponseDTO[]> => {
  const response = await apiClient.get('/comprobantes/notas-debito');
  return response.data;
};


// Liquidacion de iva

// Calcular Liquidación IVA 🔸
export const calcularLiquidacionIva = async (fechaInicio: string, fechaFin: string) => {
  const respuesta = await apiClient.get(`/liquidacion-iva/calcular?fechaInicio=${fechaInicio}&fechaFin=${fechaFin}`);
  return respuesta.data;
};

// Libro IVA Compras🔸
export const obtenerLibroIvaCompras = async (fechaInicio: string, fechaFin: string): Promise<ComprobanteResponseDTO[]> => {
  const respuesta = await apiClient.get(`/liquidacion-iva/libro-iva-compras?fechaInicio=${fechaInicio}&fechaFin=${fechaFin}`);
  return respuesta.data;
};

// Libro IVA Ventas🔸
export const obtenerLibroIvaVentas = async (fechaInicio: string, fechaFin: string): Promise<ComprobanteResponseDTO[]> => {
  const respuesta = await apiClient.get(`/liquidacion-iva/libro-iva-ventas?fechaInicio=${fechaInicio}&fechaFin=${fechaFin}`);
  return respuesta.data;
};

//Todos los Comprobantes Filtrados por tipo de operacion y tipo de comprobantes

export const obtenerComprobantesFiltrados = async (
  tipoOperacion?: 'COMPRA' | 'VENTA',
  tipoComprobante?: string,
  fechaDesde?: string,
  fechaHasta?: string
): Promise<ComprobanteResponseDTO[]> => {
  const response = await apiClient.get('/comprobantes', {
    params: { tipoOperacion, tipoComprobante, fechadesde: fechaDesde, fechaHasta }
  });
  return response.data;
};
