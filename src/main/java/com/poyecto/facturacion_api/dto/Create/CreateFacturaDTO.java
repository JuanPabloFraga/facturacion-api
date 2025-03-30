package com.poyecto.facturacion_api.dto.Create;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class CreateFacturaDTO extends CreateComprobanteDTO {
    private String letraFactura;
    private Integer puntoVenta;
}