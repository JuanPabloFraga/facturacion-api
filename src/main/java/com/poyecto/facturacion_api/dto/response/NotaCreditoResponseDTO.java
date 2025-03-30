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
public class NotaCreditoResponseDTO extends ComprobanteResponseDTO {
    private String letraNotaCredito;
    private Integer puntoVenta;
    private String motivo;
    private Long comprobanteRelacionadoId;

   
}