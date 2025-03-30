package com.poyecto.facturacion_api.controller;

import com.poyecto.facturacion_api.dto.Create.CreateClienteProveedorDTO;
import com.poyecto.facturacion_api.dto.response.ClienteProveedorResponseDTO;
import com.poyecto.facturacion_api.service.ClienteProveedorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes-proveedores")
public class ClienteProveedorController {

    @Autowired
    private ClienteProveedorService clienteProveedorService;
    
    @GetMapping
    public ResponseEntity<List<ClienteProveedorResponseDTO>> listarClientesProveedores() {
        List<ClienteProveedorResponseDTO> clientesProveedores = clienteProveedorService.findAll();
        return ResponseEntity.ok(clientesProveedores);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ClienteProveedorResponseDTO> obtenerClienteProveedorPorId(@PathVariable Long id) {
        try {
            ClienteProveedorResponseDTO clienteProveedor = clienteProveedorService.findById(id);
            return ResponseEntity.ok(clienteProveedor);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<ClienteProveedorResponseDTO> crearClienteProveedor(@Valid @RequestBody CreateClienteProveedorDTO createClienteProveedorDTO) {
        ClienteProveedorResponseDTO nuevoClienteProveedor = clienteProveedorService.save(createClienteProveedorDTO);
        return new ResponseEntity<>(nuevoClienteProveedor, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ClienteProveedorResponseDTO> actualizarClienteProveedor(
            @PathVariable Long id, 
            @Valid @RequestBody CreateClienteProveedorDTO createClienteProveedorDTO) {
        try {
            ClienteProveedorResponseDTO clienteProveedorActualizado = clienteProveedorService.update(id, createClienteProveedorDTO);
            return ResponseEntity.ok(clienteProveedorActualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarClienteProveedor(@PathVariable Long id) {
        try {
            clienteProveedorService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}