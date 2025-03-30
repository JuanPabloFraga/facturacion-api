package com.poyecto.facturacion_api.repository;

import com.poyecto.facturacion_api.model.Factura;
import com.poyecto.facturacion_api.model.TipoOperacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    
    List<Factura> findByLetraFactura(String letraFactura);
    
    List<Factura> findByTipoOperacionAndFechaBetween(
            TipoOperacion tipoOperacion, 
            LocalDate fechaInicio, 
            LocalDate fechaFin);
    
    List<Factura> findByPuntoVentaAndNumero(Integer puntoVenta, String numero);
}