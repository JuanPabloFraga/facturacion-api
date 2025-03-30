package com.poyecto.facturacion_api.dto.Create;

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
public abstract class CreateComprobanteDTO {
    private String numero;
    private LocalDate fecha;
    private TipoOperacion tipoOperacion;
    private Long clienteProveedor;
    private List<CreateDetalleComprobanteDTO> detalles;
}