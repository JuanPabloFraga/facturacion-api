package com.poyecto.facturacion_api.service;

import com.poyecto.facturacion_api.dto.Create.CreateClienteProveedorDTO;
import com.poyecto.facturacion_api.dto.response.ClienteProveedorResponseDTO;
import com.poyecto.facturacion_api.mapper.ClienteProveedorMapper;
import com.poyecto.facturacion_api.model.ClienteProveedor;
import com.poyecto.facturacion_api.repository.ClienteProveedorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteProveedorService {

    @Autowired
    private ClienteProveedorRepository clienteProveedorRepository;
    
    @Autowired
    private ClienteProveedorMapper clienteProveedorMapper;
    
    /**
     * Obtiene todos los clientes/proveedores
     * @return Lista de DTOs de respuesta con los datos de los clientes/proveedores
     */
    public List<ClienteProveedorResponseDTO> findAll() {
        return clienteProveedorRepository.findAll().stream()
                .map(clienteProveedorMapper::clienteProveedorToClienteProveedorResponseDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Busca un cliente/proveedor por su ID
     * @param id ID del cliente/proveedor a buscar
     * @return DTO de respuesta con los datos del cliente/proveedor
     * @throws EntityNotFoundException si no se encuentra el cliente/proveedor
     */
    public ClienteProveedorResponseDTO findById(Long id) {
        ClienteProveedor clienteProveedor = clienteProveedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente/Proveedor no encontrado con ID: " + id));
        return clienteProveedorMapper.clienteProveedorToClienteProveedorResponseDTO(clienteProveedor);
    }
    
    /**
     * Guarda un nuevo cliente/proveedor
     * @param createClienteProveedorDTO DTO con los datos para crear el cliente/proveedor
     * @return DTO de respuesta con los datos del cliente/proveedor creado
     */
    public ClienteProveedorResponseDTO save(CreateClienteProveedorDTO createClienteProveedorDTO) {
        // Convertir DTO a entidad
        ClienteProveedor clienteProveedor = clienteProveedorMapper.createClienteProveedorDTOToClienteProveedor(createClienteProveedorDTO);
        
        // Guardar el cliente/proveedor
        clienteProveedor = clienteProveedorRepository.save(clienteProveedor);
        
        // Convertir a DTO de respuesta y devolver
        return clienteProveedorMapper.clienteProveedorToClienteProveedorResponseDTO(clienteProveedor);
    }
    
    /**
     * Actualiza un cliente/proveedor existente
     * @param id ID del cliente/proveedor a actualizar
     * @param createClienteProveedorDTO DTO con los datos actualizados
     * @return DTO de respuesta con los datos del cliente/proveedor actualizado
     * @throws EntityNotFoundException si no se encuentra el cliente/proveedor
     */
    public ClienteProveedorResponseDTO update(Long id, CreateClienteProveedorDTO createClienteProveedorDTO) {
        // Verificar que el cliente/proveedor existe
        ClienteProveedor clienteProveedor = clienteProveedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente/Proveedor no encontrado con ID: " + id));
        
        // Actualizar los datos del cliente/proveedor
        clienteProveedorMapper.updateClienteProveedorFromDTO(createClienteProveedorDTO, clienteProveedor);
        
        // Guardar los cambios
        clienteProveedor = clienteProveedorRepository.save(clienteProveedor);
        
        // Convertir a DTO de respuesta y devolver
        return clienteProveedorMapper.clienteProveedorToClienteProveedorResponseDTO(clienteProveedor);
    }
    
    /**
     * Elimina un cliente/proveedor por su ID
     * @param id ID del cliente/proveedor a eliminar
     * @throws EntityNotFoundException si no se encuentra el cliente/proveedor
     */
    public void delete(Long id) {
        // Verificar que el cliente/proveedor existe
        if (!clienteProveedorRepository.existsById(id)) {
            throw new EntityNotFoundException("Cliente/Proveedor no encontrado con ID: " + id);
        }
        
        clienteProveedorRepository.deleteById(id);
    }
}