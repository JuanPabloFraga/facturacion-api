package com.poyecto.facturacion_api.model;

import com.poyecto.facturacion_api.factory.ImpuestoStrategyFactory;
import com.poyecto.facturacion_api.strategy.ImpuestoStrategy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "detalles_comprobante")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleComprobante {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String descripcion;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    
    // Porcentaje de IVA: 10.5, 21 o 27
    private BigDecimal porcentajeIva;
    private BigDecimal montoIva;
    
    @ManyToOne
    @JoinColumn(name = "comprobante_id")
    private Comprobante comprobante;
    
    /**
      Calcula y actualiza el subtotal basado en la cantidad y precio unitario
      @return El subtotal calculado
     */
    public BigDecimal calcularSubtotal() {
        if (cantidad != null && precioUnitario != null) {
            this.subtotal = precioUnitario.multiply(new BigDecimal(cantidad));
            return this.subtotal;
        }
        return BigDecimal.ZERO;
    }
    
    /**
      Calcula y actualiza el monto de IVA utilizando el patrón Strategy
      @return El monto de IVA calculado
     */
    public BigDecimal calcularMontoIva() {
        if (subtotal != null && porcentajeIva != null) {
            // Utilizar la estrategia adecuada según el porcentaje de IVA
            ImpuestoStrategy strategy = ImpuestoStrategyFactory.getStrategy(porcentajeIva);
            if (strategy != null) {
                this.montoIva = strategy.calcularImpuesto(subtotal);
            } else {
                // Si no hay estrategia disponible, usar el cálculo básico
                this.montoIva = subtotal.multiply(porcentajeIva.divide(new BigDecimal("100"), 4, BigDecimal.ROUND_HALF_UP));
            }
            return this.montoIva;
        }
        return BigDecimal.ZERO;
    }
    
    /**
      Actualiza los valores de subtotal y monto de IVA
      Este método es llamado automáticamente antes de persistir o actualizar
     */
    @PrePersist
    @PreUpdate
    public void actualizarValores() {
        calcularSubtotal();
        calcularMontoIva();
        
        // Notificar al comprobante para que actualice sus totales
        if (comprobante != null) {
            comprobante.actualizarTotales();
        }
    }
    
    // Método para actualizar los cálculos
    public void actualizarCalculos() {
        this.subtotal = calcularSubtotal();
        this.montoIva = calcularMontoIva();
    }
}