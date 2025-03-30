package com.poyecto.facturacion_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clientes_proveedores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteProveedor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String razonSocial;
    private String cuit;
    private String direccion;
    private String telefono;
    private String email;
    
    @Enumerated(EnumType.STRING)
    private TipoResponsable tipoResponsable;
}