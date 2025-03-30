package com.poyecto.facturacion_api.dto.response;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class FacturaResponseDTO extends ComprobanteResponseDTO {
    private String letraFactura;
    private Integer puntoVenta;

}