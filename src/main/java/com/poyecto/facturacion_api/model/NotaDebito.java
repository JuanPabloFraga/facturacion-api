package com.poyecto.facturacion_api.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("NOTA_DEBITO")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class NotaDebito extends Comprobante {
    
    private String letraNotaDebito; // A, B, C, etc.
    private Integer puntoVenta;
    private String motivo;
    
    @ManyToOne
    @JoinColumn(name = "comprobante_relacionado_id")
    private Comprobante comprobanteRelacionado;
    
    /**
     * Implementación de la regla específica para calcular el impacto del IVA en una nota de débito
     * @param montoIva El monto de IVA a considerar
     * @return El impacto fiscal del IVA (positivo para aumentar crédito fiscal, negativo para aumentar débito fiscal)
     */
    @Override
    protected BigDecimal aplicarReglaImpactoIVA(BigDecimal montoIva) {
        // Una nota de débito tiene el mismo efecto fiscal que una factura
        // Si es de compra, aumenta el crédito fiscal (positivo)
        // Si es de venta, aumenta el débito fiscal (negativo)
        if (getTipoOperacion() == TipoOperacion.COMPRA) {
            return montoIva; // Aumenta crédito fiscal
        } else {
            return montoIva.negate(); // Aumenta débito fiscal
        }
    }
}