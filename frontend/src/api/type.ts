

export interface Factura {
  id?: number;
  numero: string;
  fecha: string;
  tipoOperacion: 'COMPRA' | 'VENTA';
  clienteProveedor: number;
  letraFactura: string;
  puntoVenta: number;
  detalles: DetalleComprobante[];
}

export interface CreateFacturaDTO extends Omit<Factura, 'id'> {
  detalles: CreateDetalleComprobanteDTO[];
}

export interface DetalleComprobante {
  id?: number;
  descripcion: string;
  cantidad: number;
  precioUnitario: number;
  subtotal?: number;
  montoIva?: number;
  alicuotaIva: number;
}

export interface CreateDetalleComprobanteDTO {
  descripcion: string;
  cantidad: number;
  precioUnitario: number;
  alicuotaIva: number;
}

export interface ClienteProveedor {
  id: number;
  razonSocial: string;
  cuit: string;
  direccion: string;
  telefono: string;
  email: string;
  tipoResponsable: string;
}

export interface CreateClienteProveedorDTO {
  razonSocial: string;
  cuit: string;
  direccion: string;
  telefono: string;
  email: string;
  tipoResponsable: string;
}

export enum TipoResponsable {
  RESPONSABLE_INSCRIPTO = 'RESPONSABLE_INSCRIPTO',
  MONOTRIBUTISTA = 'MONOTRIBUTISTA',
  CONSUMIDOR_FINAL = 'CONSUMIDOR_FINAL',
  EXENTO = 'EXENTO',
}

