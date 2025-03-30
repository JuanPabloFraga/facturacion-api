package com.poyecto.facturacion_api.repository;

import com.poyecto.facturacion_api.model.ClienteProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteProveedorRepository extends JpaRepository<ClienteProveedor, Long> {
    // Métodos personalizados si son necesarios
}