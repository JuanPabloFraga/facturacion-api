package com.poyecto.facturacion_api.service;

import com.poyecto.facturacion_api.dto.response.ComprobanteResponseDTO;
import com.poyecto.facturacion_api.mapper.ComprobanteMapper;
import com.poyecto.facturacion_api.model.Comprobante;
import com.poyecto.facturacion_api.model.Factura;
import com.poyecto.facturacion_api.model.NotaCredito;
import com.poyecto.facturacion_api.model.NotaDebito;
import com.poyecto.facturacion_api.model.TipoOperacion;
import com.poyecto.facturacion_api.repository.ComprobanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LiquidacionIvaService {

    @Autowired
    private ComprobanteRepository comprobanteRepository;
    
    /**
     * Calcula la liquidación de IVA para un período específico
     * @param fechaInicio Fecha de inicio del período
     * @param fechaFin Fecha de fin del período
     * @return Mapa con los resultados de la liquidación
     */
    public Map<String, BigDecimal> calcularLiquidacionIva(LocalDate fechaInicio, LocalDate fechaFin) {
        // Obtener todos los comprobantes del período
        List<Comprobante> comprobantes = comprobanteRepository.findByFechaBetween(fechaInicio, fechaFin);
        
        // Inicializar valores
        BigDecimal creditoFiscal = BigDecimal.ZERO;
        BigDecimal debitoFiscal = BigDecimal.ZERO;
        
        // Calcular el impacto de IVA de cada comprobante
        for (Comprobante comprobante : comprobantes) {
            BigDecimal impactoIVA = comprobante.calcularImpactoIVA();
            
            // Si es positivo, suma al crédito fiscal
            // Si es negativo, suma al débito fiscal (en valor absoluto)
            if (impactoIVA.compareTo(BigDecimal.ZERO) >= 0) {
                creditoFiscal = creditoFiscal.add(impactoIVA);
            } else {
                debitoFiscal = debitoFiscal.add(impactoIVA.abs());
            }
        }
        
        // Calcular el saldo (crédito fiscal - débito fiscal)
        BigDecimal saldoIVA = creditoFiscal.subtract(debitoFiscal);
        
        // Preparar el resultado
        Map<String, BigDecimal> resultado = new HashMap<>();
        resultado.put("creditoFiscal", creditoFiscal);
        resultado.put("debitoFiscal", debitoFiscal);
        resultado.put("saldoIVA", saldoIVA);
        
        return resultado;
    }
    
    public List<ComprobanteResponseDTO> obtenerLibroIvaCompras(LocalDate fechaInicio, LocalDate fechaFin) {
        return comprobanteRepository.obtenerLibroIvaCompras(
                fechaInicio, fechaFin).stream()
                .map(comprobante -> {
                    if (comprobante instanceof Factura) {
                        return ComprobanteMapper.INSTANCE.facturaToFacturaResponseDTO((Factura) comprobante);
                    } else if (comprobante instanceof NotaCredito) {
                        return ComprobanteMapper.INSTANCE.notaCreditoToNotaCreditoResponseDTO((NotaCredito) comprobante);
                    } else if (comprobante instanceof NotaDebito) {
                        return ComprobanteMapper.INSTANCE.notaDebitoToNotaDebitoResponseDTO((NotaDebito) comprobante);
                    } else {
                        throw new IllegalArgumentException("Tipo de comprobante desconocido");
                    }
                })
                .collect(Collectors.toList());
    }
     
     
     
        
    
    
    public List<ComprobanteResponseDTO> obtenerLibroIvaVentas(LocalDate fechaInicio, LocalDate fechaFin) {
        return comprobanteRepository.obtenerLibroIvaVentas(
                fechaInicio, fechaFin).stream()
                .map(comprobante -> {
                    if (comprobante instanceof Factura) {
                        return ComprobanteMapper.INSTANCE.facturaToFacturaResponseDTO((Factura) comprobante);
                    } else if (comprobante instanceof NotaCredito) {
                        return ComprobanteMapper.INSTANCE.notaCreditoToNotaCreditoResponseDTO((NotaCredito) comprobante);
                    } else if (comprobante instanceof NotaDebito) {
                        return ComprobanteMapper.INSTANCE.notaDebitoToNotaDebitoResponseDTO((NotaDebito) comprobante);
                    } else {
                        throw new IllegalArgumentException("Tipo de comprobante desconocido");
                    }
                })
                .collect(Collectors.toList());
    }
}