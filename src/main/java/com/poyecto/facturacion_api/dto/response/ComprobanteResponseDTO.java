package com.poyecto.facturacion_api.dto.response;

import com.poyecto.facturacion_api.model.TipoOperacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class ComprobanteResponseDTO {
    private Long id;
    private String numero;
    private LocalDate fecha;
    private BigDecimal montoTotal;
    private BigDecimal montoIva;
    private TipoOperacion tipoOperacion;
    private Long clienteProveedor;
    private List<DetalleComprobanteResponseDTO> detalles;
    private String tipoComprobante;
    private BigDecimal subtotal;

    
}