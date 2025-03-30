package com.poyecto.facturacion_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_comprobante")
public abstract class Comprobante {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String numero;
    private LocalDate fecha;
    private BigDecimal montoTotal;
    private BigDecimal montoIva;
    
    @Enumerated(EnumType.STRING)
    private TipoOperacion tipoOperacion; // COMPRA o VENTA
    
    @ManyToOne
    @JoinColumn(name = "cliente_proveedor_id")
    private ClienteProveedor clienteProveedor;

    @OneToMany(mappedBy = "comprobante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleComprobante> detalles = new ArrayList<>(); // Nueva lista de detalles

    @Column(name = "tipo_comprobante", insertable = false, updatable = false)
    private String tipoComprobante;

    /**
      Método Template para calcular el impacto de IVA
      Define el algoritmo general pero delega los detalles específicos a las subclases
     */
    public BigDecimal calcularImpactoIVA() {
        // Paso 1: Obtener el monto de IVA
        BigDecimal montoIva = getMontoIva();
        
        // Paso 2: Aplicar la regla específica según el tipo de comprobante
        return aplicarReglaImpactoIVA(montoIva);
    }
    
    /**
      Método abstracto que implementan las subclases con la regla específica
      para calcular el impacto del IVA según el tipo de comprobante
     */
    protected abstract BigDecimal aplicarReglaImpactoIVA(BigDecimal montoIva);



     /* Actualiza los totales del comprobante basado en sus detalles
      Este método es llamado automáticamente cuando se modifican los detalles
    */
    @PrePersist
    @PreUpdate
    public void actualizarTotales() {
        BigDecimal nuevoSubtotal = BigDecimal.ZERO;
        BigDecimal nuevoMontoIva = BigDecimal.ZERO;
        
        if (detalles != null && !detalles.isEmpty()) {
            for (DetalleComprobante detalle : detalles) {
                if (detalle.getSubtotal() != null) {
                    nuevoSubtotal = nuevoSubtotal.add(detalle.getSubtotal());
                }
                if (detalle.getMontoIva() != null) {
                    nuevoMontoIva = nuevoMontoIva.add(detalle.getMontoIva());
                }
            }
        }
        
        this.montoIva = nuevoMontoIva;
        this.montoTotal = nuevoSubtotal.add(nuevoMontoIva);
    }
}