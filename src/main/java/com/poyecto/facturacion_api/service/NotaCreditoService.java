package com.poyecto.facturacion_api.service;

import com.poyecto.facturacion_api.dto.Create.CreateDetalleComprobanteDTO;
import com.poyecto.facturacion_api.dto.Create.CreateNotaCreditoDTO;
import com.poyecto.facturacion_api.dto.response.NotaCreditoResponseDTO;
import com.poyecto.facturacion_api.factory.ComprobanteFactory;
import com.poyecto.facturacion_api.mapper.ComprobanteMapper;
import com.poyecto.facturacion_api.model.ClienteProveedor;
import com.poyecto.facturacion_api.model.Comprobante;
import com.poyecto.facturacion_api.model.DetalleComprobante;
import com.poyecto.facturacion_api.model.NotaCredito;
import com.poyecto.facturacion_api.model.TipoOperacion;
import com.poyecto.facturacion_api.repository.ClienteProveedorRepository;
import com.poyecto.facturacion_api.repository.ComprobanteRepository;
import com.poyecto.facturacion_api.repository.NotaCreditoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotaCreditoService {

    @Autowired
    private NotaCreditoRepository notaCreditoRepository;
    
    @Autowired
    private ComprobanteRepository comprobanteRepository;
    
    @Autowired
    private ClienteProveedorRepository clienteProveedorRepository;
    
    @Autowired
    private ComprobanteMapper comprobanteMapper;
    
    public List<NotaCreditoResponseDTO> findAll() {
        return notaCreditoRepository.findAll().stream()
                .map(comprobanteMapper::notaCreditoToNotaCreditoResponseDTO)
                .collect(Collectors.toList());
    }
    
    public NotaCreditoResponseDTO findById(Long id) {
        NotaCredito notaCredito = notaCreditoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nota de Crédito no encontrada con ID: " + id));
        return comprobanteMapper.notaCreditoToNotaCreditoResponseDTO(notaCredito);
    }
    
    public List<NotaCreditoResponseDTO> findByTipoOperacion(TipoOperacion tipoOperacion) {
        return notaCreditoRepository.findByTipoOperacionAndFechaBetween(
                tipoOperacion, LocalDate.now().minusYears(1), LocalDate.now())
                .stream()
                .map(comprobanteMapper::notaCreditoToNotaCreditoResponseDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Método para guardar una nota de crédito usando el DTO de creación
     * @param createNotaCreditoDTO DTO con los datos para crear la nota de crédito
     * @return DTO de respuesta con los datos de la nota de crédito creada
     */
    public NotaCreditoResponseDTO save(CreateNotaCreditoDTO createNotaCreditoDTO) {
        // Convertir DTO a entidad
        NotaCredito notaCredito = comprobanteMapper.createNotaCreditoDTOToNotaCredito(createNotaCreditoDTO);
        
        // Establecer relaciones
        if (createNotaCreditoDTO.getClienteProveedor() != null) {
            ClienteProveedor clienteProveedor = clienteProveedorRepository.findById(createNotaCreditoDTO.getClienteProveedor())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente/Proveedor no encontrado"));
            notaCredito.setClienteProveedor(clienteProveedor);
        }
        
        if (createNotaCreditoDTO.getComprobanteRelacionadoId() != null) {
            Comprobante comprobanteRelacionado = comprobanteRepository.findById(createNotaCreditoDTO.getComprobanteRelacionadoId())
                    .orElseThrow(() -> new EntityNotFoundException("Comprobante relacionado no encontrado"));
            notaCredito.setComprobanteRelacionado(comprobanteRelacionado);
        }
        
        // Procesar los detalles
        if (createNotaCreditoDTO.getDetalles() != null && !createNotaCreditoDTO.getDetalles().isEmpty()) {
            List<DetalleComprobante> detalles = new ArrayList<>();
            BigDecimal montoTotal = BigDecimal.ZERO;
            BigDecimal montoIva = BigDecimal.ZERO;
            
            for (CreateDetalleComprobanteDTO detalleDTO : createNotaCreditoDTO.getDetalles()) {
                DetalleComprobante detalle = comprobanteMapper.createDetalleComprobanteDTOToDetalleComprobante(detalleDTO);
                detalle.setComprobante(notaCredito);
                
                // Calcular subtotal
                BigDecimal subtotal = detalleDTO.getPrecioUnitario().multiply(new BigDecimal(detalleDTO.getCantidad()));
                detalle.setSubtotal(subtotal);
                
                // Calcular IVA
                BigDecimal iva = subtotal.multiply(detalleDTO.getAlicuotaIva().divide(new BigDecimal(100)));
                detalle.setMontoIva(iva);
                
                detalles.add(detalle);
                montoTotal = montoTotal.add(subtotal);
                montoIva = montoIva.add(iva);
            }
            
            notaCredito.setDetalles(detalles);
            notaCredito.setMontoTotal(montoTotal);
            notaCredito.setMontoIva(montoIva);
        }
        
        // Guardar la nota de crédito
        notaCredito = notaCreditoRepository.save(notaCredito);
        
        // Convertir a DTO de respuesta y devolver
        return comprobanteMapper.notaCreditoToNotaCreditoResponseDTO(notaCredito);
    }
    
    
    
    public void delete(Long id) {
        notaCreditoRepository.deleteById(id);
    }
    
    /**
     * Crea una nueva nota de crédito utilizando el patrón Factory
     * @param notaCreditoDTO DTO con los datos de la nota de crédito
     * @return La nota de crédito creada
     */
    
}