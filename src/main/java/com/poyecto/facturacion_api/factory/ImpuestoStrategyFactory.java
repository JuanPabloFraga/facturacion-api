package com.poyecto.facturacion_api.factory;

import com.poyecto.facturacion_api.strategy.ImpuestoStrategy;
import com.poyecto.facturacion_api.strategy.IvaStrategy105;
import com.poyecto.facturacion_api.strategy.IvaStrategy21;
import com.poyecto.facturacion_api.strategy.IvaStrategy27;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory para la creación de estrategias de cálculo de impuestos
 */
public class ImpuestoStrategyFactory {
    
    private static final Map<BigDecimal, ImpuestoStrategy> strategies = new HashMap<>();
    
    static {
        // Inicializar las estrategias disponibles
        strategies.put(new BigDecimal("10.5"), new IvaStrategy105());
        strategies.put(new BigDecimal("21"), new IvaStrategy21());
        strategies.put(new BigDecimal("27"), new IvaStrategy27());
    }
    
    /**
     * Obtiene la estrategia de cálculo de impuesto según el porcentaje
     * @param porcentaje Porcentaje de impuesto (10.5, 21, 27)
     * @return La estrategia correspondiente o null si no existe
     */
    public static ImpuestoStrategy getStrategy(BigDecimal porcentaje) {
        if (porcentaje == null) {
            return null;
        }
        
        // Buscar la estrategia exacta
        ImpuestoStrategy strategy = strategies.get(porcentaje);
        
        // Si no se encuentra, intentar comparar los valores
        if (strategy == null) {
            for (Map.Entry<BigDecimal, ImpuestoStrategy> entry : strategies.entrySet()) {
                if (entry.getKey().compareTo(porcentaje) == 0) {
                    return entry.getValue();
                }
            }
        }
        
        return strategy;
    }
}