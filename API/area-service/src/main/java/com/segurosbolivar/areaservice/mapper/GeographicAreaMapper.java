package com.segurosbolivar.areaservice.mapper;

import com.segurosbolivar.areaservice.dto.GeographicAreaDTO;
import com.segurosbolivar.areaservice.model.GeographicArea;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeographicAreaMapper {

    @Autowired
    ModelMapper modelMapper;

    public GeographicAreaDTO geographicAreaToDTO(GeographicArea area) {
        return modelMapper.map(area, GeographicAreaDTO.class);
    }
}
