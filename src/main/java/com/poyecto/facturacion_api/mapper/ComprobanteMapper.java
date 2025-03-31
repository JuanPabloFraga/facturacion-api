package com.poyecto.facturacion_api.mapper;

import com.poyecto.facturacion_api.dto.Create.CreateComprobanteDTO;
import com.poyecto.facturacion_api.dto.Create.CreateDetalleComprobanteDTO;
import com.poyecto.facturacion_api.dto.Create.CreateFacturaDTO;
import com.poyecto.facturacion_api.dto.Create.CreateNotaCreditoDTO;
import com.poyecto.facturacion_api.dto.Create.CreateNotaDebitoDTO;
import com.poyecto.facturacion_api.dto.response.ComprobanteResponseDTO;
import com.poyecto.facturacion_api.dto.response.DetalleComprobanteResponseDTO;
import com.poyecto.facturacion_api.dto.response.FacturaResponseDTO;
import com.poyecto.facturacion_api.dto.response.NotaCreditoResponseDTO;
import com.poyecto.facturacion_api.dto.response.NotaDebitoResponseDTO;
import com.poyecto.facturacion_api.model.Comprobante;
import com.poyecto.facturacion_api.model.DetalleComprobante;
import com.poyecto.facturacion_api.model.Factura;
import com.poyecto.facturacion_api.model.NotaCredito;
import com.poyecto.facturacion_api.model.NotaDebito;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

 //Interfaz de MapStruct para mapear entre entidades y DTOs de comprobantes



@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface ComprobanteMapper {
    
    // ========== Mapeos para DTOs de respuesta ==========
    
    ComprobanteMapper INSTANCE = Mappers.getMapper(ComprobanteMapper.class);


     // Convierte una Factura a FacturaResponseDTO

    @Mapping(target = "clienteProveedor", source = "clienteProveedor.id")
    @Mapping(target = "tipoComprobante", ignore = true)
    @Mapping(target = "montoTotal", source = "montoTotal")
    @Mapping(target = "subtotal", source = "subtotal")
    FacturaResponseDTO facturaToFacturaResponseDTO(Factura factura);
    

     // Convierte una NotaCredito a NotaCreditoResponseDTO

    @Mapping(target = "clienteProveedor", source = "clienteProveedor.id")
    @Mapping(target = "tipoComprobante", ignore = true)
    @Mapping(target = "comprobanteRelacionadoId", source = "comprobanteRelacionado.id")
    NotaCreditoResponseDTO notaCreditoToNotaCreditoResponseDTO(NotaCredito notaCredito);
    

    //  Convierte una NotaDebito a NotaDebitoResponseDTO

    @Mapping(target = "clienteProveedor", source = "clienteProveedor.id")
    @Mapping(target = "comprobanteRelacionadoId", source = "comprobanteRelacionado.id")
    @Mapping(target = "tipoComprobante", ignore = true)
    NotaDebitoResponseDTO notaDebitoToNotaDebitoResponseDTO(NotaDebito notaDebito);
    

     // Convierte un DetalleComprobante a DetalleComprobanteResponseDTO

    DetalleComprobanteResponseDTO detalleComprobanteToDetalleComprobanteResponseDTO(DetalleComprobante detalleComprobante);
    

     //Convierte una lista de DetalleComprobante a lista de DetalleComprobanteResponseDTO

    List<DetalleComprobanteResponseDTO> detalleComprobantesToDetalleComprobanteResponseDTOs(List<DetalleComprobante> detalles);
    

    

     // Convierte un CreateFacturaDTO a Factura

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "montoIva", ignore = true)
    @Mapping(target = "clienteProveedor", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    Factura createFacturaDTOToFactura(CreateFacturaDTO createFacturaDTO);
    

    // Convierte un CreateNotaCreditoDTO a NotaCredito

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "montoIva", ignore = true)
    @Mapping(target = "clienteProveedor", ignore = true)
    @Mapping(target = "comprobanteRelacionado", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    NotaCredito createNotaCreditoDTOToNotaCredito(CreateNotaCreditoDTO createNotaCreditoDTO);
    

    //Convierte un CreateNotaDebitoDTO a NotaDebito

    @Mapping(target = "id", ignore = true)
 
    @Mapping(target = "montoIva", ignore = true)
    @Mapping(target = "clienteProveedor", ignore = true)
    @Mapping(target = "comprobanteRelacionado", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    NotaDebito createNotaDebitoDTOToNotaDebito(CreateNotaDebitoDTO createNotaDebitoDTO);
    

     // Convierte un CreateDetalleComprobanteDTO a DetalleComprobante

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    @Mapping(target = "montoIva", ignore = true)
    @Mapping(target = "comprobante", ignore = true)
    DetalleComprobante createDetalleComprobanteDTOToDetalleComprobante(CreateDetalleComprobanteDTO createDetalleComprobanteDTO);
    

     // Convierte una lista de CreateDetalleComprobanteDTO a lista de DetalleComprobante

    List<DetalleComprobante> createDetalleComprobanteDTOsToDetalleComprobantes(List<CreateDetalleComprobanteDTO> detalles);
    







}