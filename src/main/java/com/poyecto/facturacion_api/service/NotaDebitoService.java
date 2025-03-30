package com.poyecto.facturacion_api.service;

import com.poyecto.facturacion_api.dto.Create.CreateDetalleComprobanteDTO;
import com.poyecto.facturacion_api.dto.Create.CreateNotaDebitoDTO;

import com.poyecto.facturacion_api.dto.response.NotaDebitoResponseDTO;
import com.poyecto.facturacion_api.factory.ComprobanteFactory;
import com.poyecto.facturacion_api.mapper.ComprobanteMapper;
import com.poyecto.facturacion_api.model.ClienteProveedor;
import com.poyecto.facturacion_api.model.Comprobante;
import com.poyecto.facturacion_api.model.DetalleComprobante;
import com.poyecto.facturacion_api.model.NotaDebito;
import com.poyecto.facturacion_api.model.TipoOperacion;
import com.poyecto.facturacion_api.repository.ClienteProveedorRepository;
import com.poyecto.facturacion_api.repository.ComprobanteRepository;
import com.poyecto.facturacion_api.repository.NotaDebitoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotaDebitoService {

    @Autowired
    private NotaDebitoRepository notaDebitoRepository;
    
    @Autowired
    private ComprobanteRepository comprobanteRepository;
    
    @Autowired
    private ClienteProveedorRepository clienteProveedorRepository;
    
    @Autowired
    private ComprobanteMapper comprobanteMapper;
    
    public List<NotaDebitoResponseDTO> findAll() {
        return notaDebitoRepository.findAll().stream()
                .map(comprobanteMapper::notaDebitoToNotaDebitoResponseDTO)
                .collect(Collectors.toList());
    }
    
    public NotaDebitoResponseDTO findById(Long id) {
        NotaDebito notaDebito = notaDebitoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nota de Débito no encontrada con ID: " + id));
        return comprobanteMapper.notaDebitoToNotaDebitoResponseDTO(notaDebito);
    }
    
    public List<NotaDebitoResponseDTO> findByTipoOperacion(TipoOperacion tipoOperacion) {
        return notaDebitoRepository.findByTipoOperacionAndFechaBetween(
                tipoOperacion, LocalDate.now().minusYears(1), LocalDate.now())
                .stream()
                .map(comprobanteMapper::notaDebitoToNotaDebitoResponseDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Método para guardar una nota de débito usando el DTO de creación
     * @param createNotaDebitoDTO DTO con los datos para crear la nota de débito
     * @return DTO de respuesta con los datos de la nota de débito creada
     */
    public NotaDebitoResponseDTO save(CreateNotaDebitoDTO createNotaDebitoDTO) {
        // Convertir DTO a entidad
        NotaDebito notaDebito = comprobanteMapper.createNotaDebitoDTOToNotaDebito(createNotaDebitoDTO);
        
        // Establecer relaciones
        if (createNotaDebitoDTO.getClienteProveedor() != null) {
            ClienteProveedor clienteProveedor = clienteProveedorRepository.findById(createNotaDebitoDTO.getClienteProveedor())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente/Proveedor no encontrado"));
            notaDebito.setClienteProveedor(clienteProveedor);
        }
        
        if (createNotaDebitoDTO.getComprobanteRelacionadoId() != null) {
            Comprobante comprobanteRelacionado = comprobanteRepository.findById(createNotaDebitoDTO.getComprobanteRelacionadoId())
                    .orElseThrow(() -> new EntityNotFoundException("Comprobante relacionado no encontrado"));
            notaDebito.setComprobanteRelacionado(comprobanteRelacionado);
        }
        
        // Procesar los detalles
        if (createNotaDebitoDTO.getDetalles() != null && !createNotaDebitoDTO.getDetalles().isEmpty()) {
            List<DetalleComprobante> detalles = new ArrayList<>();
            BigDecimal montoTotal = BigDecimal.ZERO;
            BigDecimal montoIva = BigDecimal.ZERO;
            
            for (CreateDetalleComprobanteDTO detalleDTO : createNotaDebitoDTO.getDetalles()) {
                DetalleComprobante detalle = comprobanteMapper.createDetalleComprobanteDTOToDetalleComprobante(detalleDTO);
                detalle.setComprobante(notaDebito);
                
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
            
            notaDebito.setDetalles(detalles);
            notaDebito.setMontoTotal(montoTotal);
            notaDebito.setMontoIva(montoIva);
        }
        
        // Guardar la nota de débito
        notaDebito = notaDebitoRepository.save(notaDebito);
        
        // Convertir a DTO de respuesta y devolver
        return comprobanteMapper.notaDebitoToNotaDebitoResponseDTO(notaDebito);
    }
    
    
    
    public void delete(Long id) {
        notaDebitoRepository.deleteById(id);
    }
    
   
}