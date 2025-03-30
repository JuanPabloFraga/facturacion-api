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
@DiscriminatorValue("NOTA_CREDITO")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class NotaCredito extends Comprobante {
    
    private String letraNotaCredito; // A, B, C, etc.
    private Integer puntoVenta;
    private String motivo;
    
    @ManyToOne
    @JoinColumn(name = "comprobante_relacionado_id")
    private Comprobante comprobanteRelacionado;
    
    /**
     * Implementación de la regla específica para calcular el impacto del IVA en una nota de crédito
     * @param montoIva El monto de IVA a considerar
     * @return El impacto fiscal del IVA (negativo para reducir crédito fiscal, positivo para reducir débito fiscal)
     */
    @Override
    protected BigDecimal aplicarReglaImpactoIVA(BigDecimal montoIva) {
        // Una nota de crédito invierte el efecto fiscal de la operación original
        // Si es de compra, reduce el crédito fiscal (negativo)
        // Si es de venta, reduce el débito fiscal (positivo)
        if (getTipoOperacion() == TipoOperacion.COMPRA) {
            return montoIva.negate(); // Reduce crédito fiscal
        } else {
            return montoIva; // Reduce débito fiscal
        }
    }
}