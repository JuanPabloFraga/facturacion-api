package com.poyecto.facturacion_api.componentes;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import com.poyecto.facturacion_api.model.TipoOperacion;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToTipoOperacionConverter implements Converter<String, TipoOperacion> {
    @Override
    public TipoOperacion convert(String source) {
        return TipoOperacion.valueOf(source.toUpperCase());
    }


}