package com.poyecto.facturacion_api.service;

import com.poyecto.facturacion_api.dto.Create.CreateDetalleComprobanteDTO;
import com.poyecto.facturacion_api.dto.Create.CreateFacturaDTO;

import com.poyecto.facturacion_api.dto.response.FacturaResponseDTO;
import com.poyecto.facturacion_api.factory.ComprobanteFactory;
import com.poyecto.facturacion_api.mapper.ComprobanteMapper;
import com.poyecto.facturacion_api.model.ClienteProveedor;
import com.poyecto.facturacion_api.model.DetalleComprobante;
import com.poyecto.facturacion_api.model.Factura;
import com.poyecto.facturacion_api.model.TipoOperacion;
import com.poyecto.facturacion_api.repository.ClienteProveedorRepository;
import com.poyecto.facturacion_api.repository.FacturaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;
    
    @Autowired
    private ClienteProveedorRepository clienteProveedorRepository;
    
    @Autowired
    private ComprobanteMapper comprobanteMapper;
    
    public List<FacturaResponseDTO> findAll() {
        return facturaRepository.findAll().stream()
                .map(comprobanteMapper::facturaToFacturaResponseDTO)
                .collect(Collectors.toList());
    }
    
    public FacturaResponseDTO findById(Long id) {
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada con ID: " + id));
        return comprobanteMapper.facturaToFacturaResponseDTO(factura);
    }
    
    public List<FacturaResponseDTO> findByTipoOperacion(TipoOperacion tipoOperacion) {
        return facturaRepository.findByTipoOperacionAndFechaBetween(
                tipoOperacion, LocalDate.now().minusYears(1), LocalDate.now())
                .stream()
                .map(comprobanteMapper::facturaToFacturaResponseDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Método para guardar una factura usando el DTO de creación
     * @param createFacturaDTO DTO con los datos para crear la factura
     * @return DTO de respuesta con los datos de la factura creada
     */
    public FacturaResponseDTO save(CreateFacturaDTO createFacturaDTO) {
        // Convertir DTO a entidad
        Factura factura = comprobanteMapper.createFacturaDTOToFactura(createFacturaDTO);
        
        // Establecer relaciones
        if (createFacturaDTO.getClienteProveedor() != null) {
            ClienteProveedor clienteProveedor = clienteProveedorRepository.findById(createFacturaDTO.getClienteProveedor())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente/Proveedor no encontrado"));
            factura.setClienteProveedor(clienteProveedor);
        }
        
        // Procesar los detalles
        if (createFacturaDTO.getDetalles() != null && !createFacturaDTO.getDetalles().isEmpty()) {
            List<DetalleComprobante> detalles = new ArrayList<>();
            BigDecimal montoTotal = BigDecimal.ZERO;
            BigDecimal montoIva = BigDecimal.ZERO;
            BigDecimal alicuota = BigDecimal.ZERO;
            BigDecimal montoSubtotal = BigDecimal.ZERO;

            for (CreateDetalleComprobanteDTO detalleDTO : createFacturaDTO.getDetalles()) {
                
                DetalleComprobante detalle = comprobanteMapper.createDetalleComprobanteDTOToDetalleComprobante(detalleDTO);
                detalle.setComprobante(factura);
                
                // Calcular subtotal
                BigDecimal subtotal = detalleDTO.getPrecioUnitario().multiply(new BigDecimal(detalleDTO.getCantidad()));
                detalle.setSubtotal(subtotal);
                
                // Calcular IVA
                BigDecimal iva = subtotal.multiply(detalleDTO.getAlicuotaIva().divide(new BigDecimal(100)));
                detalle.setMontoIva(iva);
                
                detalles.add(detalle);
               
                montoIva = montoIva.add(iva);
                montoSubtotal = montoSubtotal.add(subtotal);
                montoTotal = montoTotal.add(subtotal).add(montoIva);
            }
            
            factura.setDetalles(detalles);
            
            factura.setMontoIva(montoIva);
            factura.setMontoTotal(montoTotal);
        }
        
        // Guardar la factura
        factura = facturaRepository.save(factura);
        
        // Convertir a DTO de respuesta y devolver
        return comprobanteMapper.facturaToFacturaResponseDTO(factura);
    }
    
    public void delete(Long id) {
        facturaRepository.deleteById(id);
    }
    
  
    
}