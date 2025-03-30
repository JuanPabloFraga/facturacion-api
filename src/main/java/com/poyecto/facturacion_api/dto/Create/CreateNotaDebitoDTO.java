package com.poyecto.facturacion_api.dto.Create;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class CreateNotaDebitoDTO extends CreateComprobanteDTO {
    private String letraNotaDebito;
    private Integer puntoVenta;
    private String motivo;
    private Long comprobanteRelacionadoId;
}