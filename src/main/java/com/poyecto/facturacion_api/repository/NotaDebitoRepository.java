package com.poyecto.facturacion_api.repository;

import com.poyecto.facturacion_api.model.NotaDebito;
import com.poyecto.facturacion_api.model.TipoOperacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NotaDebitoRepository extends JpaRepository<NotaDebito, Long> {
    
    List<NotaDebito> findByLetraNotaDebito(String letraNotaDebito);
    
    List<NotaDebito> findByTipoOperacionAndFechaBetween(
            TipoOperacion tipoOperacion, 
            LocalDate fechaInicio, 
            LocalDate fechaFin);
    
    List<NotaDebito> findByComprobanteRelacionadoId(Long comprobanteRelacionadoId);
}