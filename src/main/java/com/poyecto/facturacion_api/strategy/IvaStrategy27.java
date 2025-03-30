package com.poyecto.facturacion_api.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Implementación de la estrategia para el cálculo de IVA al 27%
 */
public class IvaStrategy27 implements ImpuestoStrategy {
    
    private static final BigDecimal PORCENTAJE = new BigDecimal("27");
    
    @Override
    public BigDecimal calcularImpuesto(BigDecimal subtotal) {
        if (subtotal == null) {
            return BigDecimal.ZERO;
        }
        return subtotal.multiply(PORCENTAJE.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP))
                .setScale(2, RoundingMode.HALF_UP);
    }
    
    @Override
    public BigDecimal getPorcentaje() {
        return PORCENTAJE;
    }
}