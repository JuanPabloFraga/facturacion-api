package com.poyecto.facturacion_api.repository;

import com.poyecto.facturacion_api.model.NotaCredito;
import com.poyecto.facturacion_api.model.TipoOperacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NotaCreditoRepository extends JpaRepository<NotaCredito, Long> {
    
    List<NotaCredito> findByLetraNotaCredito(String letraNotaCredito);
    
    List<NotaCredito> findByTipoOperacionAndFechaBetween(
            TipoOperacion tipoOperacion, 
            LocalDate fechaInicio, 
            LocalDate fechaFin);
    
    List<NotaCredito> findByComprobanteRelacionadoId(Long comprobanteRelacionadoId);
}