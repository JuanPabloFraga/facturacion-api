package com.poyecto.facturacion_api.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("FACTURA")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class Factura extends Comprobante {
    
    private String letraFactura; // A, B, C, etc.
    private Integer puntoVenta;
    private BigDecimal subtotal;
    
    /**
      Implementacion de la regla especifica para calcular el impacto del IVA en una factura

        El impacto fiscal del IVA (positivo para credito fiscal negativo para debito fiscal)
     */
    @Override
    protected BigDecimal aplicarReglaImpactoIVA(BigDecimal montoIva) {
        // Si es una factura de compra genera credito fiscal
        // Si es una factura de venta genera debito fiscal
        if (getTipoOperacion() == TipoOperacion.COMPRA) {
            return montoIva; // Credito fiscal
        } else {
            return montoIva.negate(); // Debito fiscal (negativo)
        }
    }
}