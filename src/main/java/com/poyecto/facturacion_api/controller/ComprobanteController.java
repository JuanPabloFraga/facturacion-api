package com.poyecto.facturacion_api.controller;

import com.poyecto.facturacion_api.dto.Create.CreateFacturaDTO;
import com.poyecto.facturacion_api.dto.Create.CreateNotaCreditoDTO;
import com.poyecto.facturacion_api.dto.Create.CreateNotaDebitoDTO;
import com.poyecto.facturacion_api.dto.response.ComprobanteResponseDTO;
import com.poyecto.facturacion_api.dto.response.FacturaResponseDTO;
import com.poyecto.facturacion_api.dto.response.NotaCreditoResponseDTO;
import com.poyecto.facturacion_api.dto.response.NotaDebitoResponseDTO;
import com.poyecto.facturacion_api.model.Factura;
import com.poyecto.facturacion_api.model.NotaCredito;
import com.poyecto.facturacion_api.model.NotaDebito;
import com.poyecto.facturacion_api.model.TipoOperacion;
import com.poyecto.facturacion_api.service.ComprobanteService;
import com.poyecto.facturacion_api.service.FacturaService;
import com.poyecto.facturacion_api.service.NotaCreditoService;
import com.poyecto.facturacion_api.service.NotaDebitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityNotFoundException;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/comprobantes")
public class ComprobanteController {

    @Autowired
    private FacturaService facturaService;
    
    @Autowired
    private NotaCreditoService notaCreditoService;
    
    @Autowired
    private NotaDebitoService notaDebitoService;

    @Autowired
    private ComprobanteService comprobanteService;
    
    // Endpoints para Facturas
    @PostMapping("/facturas")
    public ResponseEntity<FacturaResponseDTO> crearFactura(@Valid @RequestBody CreateFacturaDTO createFacturaDTO) {
        FacturaResponseDTO nuevaFactura = facturaService.save(createFacturaDTO);
        return new ResponseEntity<>(nuevaFactura, HttpStatus.CREATED);
    }
    
    @GetMapping("/facturas")
    public ResponseEntity<List<FacturaResponseDTO>> listarFacturas() {
        List<FacturaResponseDTO> facturas = facturaService.findAll();
        return ResponseEntity.ok(facturas);
    }
    
    @GetMapping("/facturas/{id}")
    public ResponseEntity<FacturaResponseDTO> obtenerFacturaPorId(@PathVariable Long id) {
        try {
            FacturaResponseDTO factura = facturaService.findById(id);
            return ResponseEntity.ok(factura);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Endpoints para Notas de Crédito
    @PostMapping("/notas-credito")
    public ResponseEntity<NotaCreditoResponseDTO> crearNotaCredito(@Valid @RequestBody CreateNotaCreditoDTO createNotaCreditoDTO) {
        NotaCreditoResponseDTO nuevaNotaCredito = notaCreditoService.save(createNotaCreditoDTO);
        return new ResponseEntity<>(nuevaNotaCredito, HttpStatus.CREATED);
    }
    
    @GetMapping("/notas-credito")
    public ResponseEntity<List<NotaCreditoResponseDTO>> listarNotasCredito() {
        List<NotaCreditoResponseDTO> notasCredito = notaCreditoService.findAll();
        return ResponseEntity.ok(notasCredito);
    }
    
    @GetMapping("/notas-credito/{id}")
    public ResponseEntity<NotaCreditoResponseDTO> obtenerNotaCreditoPorId(@PathVariable Long id) {
        try {
            NotaCreditoResponseDTO notaCredito = notaCreditoService.findById(id);
            return ResponseEntity.ok(notaCredito);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Endpoints para Notas de Débito
    @PostMapping("/notas-debito")
    public ResponseEntity<NotaDebitoResponseDTO> crearNotaDebito(@Valid @RequestBody CreateNotaDebitoDTO createNotaDebitoDTO) {
        NotaDebitoResponseDTO nuevaNotaDebito = notaDebitoService.save(createNotaDebitoDTO);
        return new ResponseEntity<>(nuevaNotaDebito, HttpStatus.CREATED);
    }
    
    @GetMapping("/notas-debito")
    public ResponseEntity<List<NotaDebitoResponseDTO>> listarNotasDebito() {
        List<NotaDebitoResponseDTO> notasDebito = notaDebitoService.findAll();
        return ResponseEntity.ok(notasDebito);
    }
    
    @GetMapping("/notas-debito/{id}")
    public ResponseEntity<NotaDebitoResponseDTO> obtenerNotaDebitoPorId(@PathVariable Long id) {
        try {
            NotaDebitoResponseDTO notaDebito = notaDebitoService.findById(id);
            return ResponseEntity.ok(notaDebito);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping
    public ResponseEntity<List<ComprobanteResponseDTO>> obtenerTodosComprobantes(
            @RequestParam(required = false) TipoOperacion tipoOperacion,
            @RequestParam(required = false) String tipoComprobante,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechadesde,
            @RequestParam(required = false)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaHasta
            ){


        List<ComprobanteResponseDTO> comprobantes = comprobanteService.obtenerComprobantesFiltrados(tipoOperacion, tipoComprobante,fechadesde,fechaHasta);
        return ResponseEntity.ok(comprobantes);
    }
}