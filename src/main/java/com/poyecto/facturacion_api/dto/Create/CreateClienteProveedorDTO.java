package com.poyecto.facturacion_api.dto.Create;

import com.poyecto.facturacion_api.model.TipoResponsable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateClienteProveedorDTO {
    private String razonSocial;
    private String cuit;
    private String direccion;
    private String telefono;
    private String email;
    private TipoResponsable tipoResponsable;
}