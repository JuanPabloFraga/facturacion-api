package com.poyecto.facturacion_api.repository;

import com.poyecto.facturacion_api.dto.response.ComprobanteResponseDTO;
import com.poyecto.facturacion_api.model.Comprobante;
import com.poyecto.facturacion_api.model.TipoOperacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ComprobanteRepository extends JpaRepository<Comprobante, Long> {


    @Query("SELECT c FROM Comprobante c WHERE (:tipoOperacion IS NULL OR c.tipoOperacion = :tipoOperacion) AND (:tipoComprobante IS NULL OR c.tipoComprobante = :tipoComprobante)")
    List<Comprobante> obtenerComprobantesFiltrados(@Param("tipoOperacion") TipoOperacion tipoOperacion,
                                                   @Param("tipoComprobante") String tipoComprobante);



    List<Comprobante> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    List<Comprobante> findByTipoOperacionAndFechaBetween(
            TipoOperacion tipoOperacion, 
            LocalDate fechaInicio, 
            LocalDate fechaFin);
    
    @Query("SELECT c FROM Comprobante c WHERE c.clienteProveedor.id = :clienteProveedorId")
    List<Comprobante> findByClienteProveedorId(Long clienteProveedorId);
   
    @Query("SELECT c FROM Comprobante c WHERE c.tipoOperacion = 'COMPRA' AND c.fecha BETWEEN :fechaInicio AND :fechaFin")
    public List<Comprobante> obtenerLibroIvaCompras(
            @Param("fechaInicio") LocalDate fechaInicio,
     @Param("fechaFin") LocalDate fechaFin);

     @Query("SELECT c FROM Comprobante c WHERE c.tipoOperacion = 'VENTA' AND c.fecha BETWEEN :fechaInicio AND :fechaFin")
     public List<Comprobante> obtenerLibroIvaVentas(
             @Param("fechaInicio") LocalDate fechaInicio,
      @Param("fechaFin") LocalDate fechaFin);


}