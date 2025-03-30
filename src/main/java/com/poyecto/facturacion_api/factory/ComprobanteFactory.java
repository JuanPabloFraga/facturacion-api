package com.poyecto.facturacion_api.factory;

import com.poyecto.facturacion_api.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Factory para la creación de diferentes tipos de comprobantes
 */
public class ComprobanteFactory {
    
    /**
     * Crea una factura con los datos básicos
     * @param numero Número de factura
     * @param fecha Fecha de emisión
     * @param tipoOperacion Tipo de operación (COMPRA/VENTA)
     * @param letraFactura Letra de factura (A, B, C)
     * @param puntoVenta Punto de venta
     * @param clienteProveedor Cliente o proveedor asociado
     * @return Una nueva instancia de Factura
     */
    public static Factura crearFactura(String numero, LocalDate fecha, TipoOperacion tipoOperacion,
                                      String letraFactura, Integer puntoVenta, ClienteProveedor clienteProveedor) {
        return Factura.builder()
                .numero(numero)
                .fecha(fecha)
                .tipoOperacion(tipoOperacion)
                .letraFactura(letraFactura)
                .puntoVenta(puntoVenta)
                .clienteProveedor(clienteProveedor)
                .montoTotal(BigDecimal.ZERO) // Se calculará automáticamente
                .montoIva(BigDecimal.ZERO)   // Se calculará automáticamente
                .subtotal(BigDecimal.ZERO)   // Se calculará automáticamente
                .build();
    }
    
    /**
     * Crea una nota de crédito con los datos básicos
     * @param numero Número de nota de crédito
     * @param fecha Fecha de emisión
     * @param tipoOperacion Tipo de operación (COMPRA/VENTA)
     * @param letraNotaCredito Letra de nota de crédito (A, B, C)
     * @param puntoVenta Punto de venta
     * @param motivo Motivo de la nota de crédito
     * @param comprobanteRelacionado Comprobante relacionado
     * @param clienteProveedor Cliente o proveedor asociado
     * @return Una nueva instancia de NotaCredito
     */
    public static NotaCredito crearNotaCredito(String numero, LocalDate fecha, TipoOperacion tipoOperacion,
                                              String letraNotaCredito, Integer puntoVenta, String motivo,
                                              Comprobante comprobanteRelacionado, ClienteProveedor clienteProveedor) {
        return NotaCredito.builder()
                .numero(numero)
                .fecha(fecha)
                .tipoOperacion(tipoOperacion)
                .letraNotaCredito(letraNotaCredito)
                .puntoVenta(puntoVenta)
                .motivo(motivo)
                .comprobanteRelacionado(comprobanteRelacionado)
                .clienteProveedor(clienteProveedor)
                .montoTotal(BigDecimal.ZERO) // Se calculará automáticamente
                .montoIva(BigDecimal.ZERO)   // Se calculará automáticamente
                .build();
    }
    
    /**
     * Crea una nota de débito con los datos básicos
     * @param numero Número de nota de débito
     * @param fecha Fecha de emisión
     * @param tipoOperacion Tipo de operación (COMPRA/VENTA)
     * @param letraNotaDebito Letra de nota de débito (A, B, C)
     * @param puntoVenta Punto de venta
     * @param motivo Motivo de la nota de débito
     * @param comprobanteRelacionado Comprobante relacionado
     * @param clienteProveedor Cliente o proveedor asociado
     * @return Una nueva instancia de NotaDebito
     */
    public static NotaDebito crearNotaDebito(String numero, LocalDate fecha, TipoOperacion tipoOperacion,
                                            String letraNotaDebito, Integer puntoVenta, String motivo,
                                            Comprobante comprobanteRelacionado, ClienteProveedor clienteProveedor) {
        return NotaDebito.builder()
                .numero(numero)
                .fecha(fecha)
                .tipoOperacion(tipoOperacion)
                .letraNotaDebito(letraNotaDebito)
                .puntoVenta(puntoVenta)
                .motivo(motivo)
                .comprobanteRelacionado(comprobanteRelacionado)
                .clienteProveedor(clienteProveedor)
                .montoTotal(BigDecimal.ZERO) // Se calculará automáticamente
                .montoIva(BigDecimal.ZERO)   // Se calculará automáticamente
                .build();
    }
}