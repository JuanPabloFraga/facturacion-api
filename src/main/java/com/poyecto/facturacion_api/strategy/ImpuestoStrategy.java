package com.poyecto.facturacion_api.strategy;

import java.math.BigDecimal;

/**
 * Interfaz que define la estrategia para el cálculo de impuestos
 */
public interface ImpuestoStrategy {
    
    /**
     * Calcula el monto del impuesto basado en el subtotal
     * @param subtotal El subtotal sobre el cual calcular el impuesto
     * @return El monto del impuesto calculado
     */
    BigDecimal calcularImpuesto(BigDecimal subtotal);
    
    /**
     * Obtiene el porcentaje de impuesto que aplica esta estrategia
     * @return El porcentaje de impuesto
     */
    BigDecimal getPorcentaje();
}