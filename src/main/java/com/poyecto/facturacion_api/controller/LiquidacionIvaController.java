package com.poyecto.facturacion_api.controller;

import com.poyecto.facturacion_api.dto.response.ComprobanteResponseDTO;
import com.poyecto.facturacion_api.model.Comprobante;
import com.poyecto.facturacion_api.service.LiquidacionIvaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/liquidacion-iva")
public class LiquidacionIvaController {

    @Autowired
    private LiquidacionIvaService liquidacionIvaService;
    
    @GetMapping("/calcular")
    public ResponseEntity<Map<String, BigDecimal>> calcularLiquidacionIva(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        
        Map<String, BigDecimal> resultado = liquidacionIvaService.calcularLiquidacionIva(fechaInicio, fechaFin);
        return ResponseEntity.ok(resultado);
    }
    
    @GetMapping("/libro-iva-compras")
    public ResponseEntity<List<ComprobanteResponseDTO>> obtenerLibroIvaCompras(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        
        List<ComprobanteResponseDTO> comprobantes = liquidacionIvaService.obtenerLibroIvaCompras(fechaInicio, fechaFin);
        return ResponseEntity.ok(comprobantes);
    }
    
    @GetMapping("/libro-iva-ventas")
    public ResponseEntity<List<ComprobanteResponseDTO>> obtenerLibroIvaVentas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        
        List<ComprobanteResponseDTO> comprobantes = liquidacionIvaService.obtenerLibroIvaVentas(fechaInicio, fechaFin);
        return ResponseEntity.ok(comprobantes);
    }
}