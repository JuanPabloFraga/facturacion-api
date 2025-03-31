package com.poyecto.facturacion_api.service;

import com.poyecto.facturacion_api.dto.response.ComprobanteResponseDTO;
import com.poyecto.facturacion_api.mapper.ComprobanteMapper;
import com.poyecto.facturacion_api.model.*;
import com.poyecto.facturacion_api.repository.ComprobanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComprobanteService {
    @Autowired
    private final ComprobanteRepository comprobanteRepository;

    @Autowired
    private final ComprobanteMapper comprobanteMapper;


    public ComprobanteService(ComprobanteRepository comprobanteRepository, ComprobanteMapper comprobanteMapper) {
        this.comprobanteRepository = comprobanteRepository;
        this.comprobanteMapper = comprobanteMapper;

    }



    public List<ComprobanteResponseDTO> obtenerComprobantesFiltrados(TipoOperacion tipoOperacion, String tipoComprobante, LocalDate fechaDesde, LocalDate fechaHasta) {
        return comprobanteRepository.obtenerComprobantesFiltrados(tipoOperacion, tipoComprobante).stream()
                .filter(comprobante -> comprobante.getFecha() != null &&
                        (fechaDesde == null || !comprobante.getFecha().isBefore(fechaDesde)) &&
                        (fechaHasta == null || !comprobante.getFecha().isAfter(fechaHasta))
                )
                .map(this::mapearAComprobanteResponseDTO)
                .collect(Collectors.toList());
    }
    private ComprobanteResponseDTO mapearAComprobanteResponseDTO(Comprobante comprobante) {
        ComprobanteResponseDTO dto;

        if (comprobante instanceof Factura factura) {
            dto = comprobanteMapper.facturaToFacturaResponseDTO(factura);
            dto.setTipoComprobante("FAC");
            dto.setSubtotal(factura.getMontoTotal().subtract(factura.getMontoIva()));

        } else if (comprobante instanceof NotaCredito notaCredito) {

            dto =  comprobanteMapper.notaCreditoToNotaCreditoResponseDTO(notaCredito);
            dto.setTipoComprobante("NC");

        } else if (comprobante instanceof NotaDebito notaDebito) {

            dto = comprobanteMapper.notaDebitoToNotaDebitoResponseDTO(notaDebito);
            dto.setTipoComprobante("ND");
        } else {
            throw new IllegalArgumentException("Tipo de comprobante desconocido: " + comprobante.getClass().getSimpleName());
        }


        return dto;


    }

}
