package com.poyecto.facturacion_api.mapper;

import com.poyecto.facturacion_api.dto.Create.CreateClienteProveedorDTO;
import com.poyecto.facturacion_api.dto.response.ClienteProveedorResponseDTO;
import com.poyecto.facturacion_api.model.ClienteProveedor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;


 // Interfaz de MapStruct para mapear entre ClienteProveedor y sus DTOs

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClienteProveedorMapper {


     // Convierte un ClienteProveedor a ClienteProveedorResponseDTO

    ClienteProveedorResponseDTO clienteProveedorToClienteProveedorResponseDTO(ClienteProveedor clienteProveedor);


     // Convierte un CreateClienteProveedorDTO a ClienteProveedor

    @Mapping(target = "id", ignore = true)
    ClienteProveedor createClienteProveedorDTOToClienteProveedor(CreateClienteProveedorDTO createClienteProveedorDTO);


     // Actualiza un ClienteProveedor existente con datos de un CreateClienteProveedorDTO

    void updateClienteProveedorFromDTO(CreateClienteProveedorDTO createClienteProveedorDTO, @MappingTarget ClienteProveedor clienteProveedor);
}