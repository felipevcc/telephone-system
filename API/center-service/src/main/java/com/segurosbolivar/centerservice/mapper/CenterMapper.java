package com.segurosbolivar.centerservice.mapper;

import com.segurosbolivar.centerservice.dto.CenterDTO;
import com.segurosbolivar.centerservice.model.Center;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CenterMapper {

    @Autowired
    ModelMapper modelMapper;

    public CenterDTO centerToDTO(Center center) {
        return modelMapper.map(center, CenterDTO.class);
    }
}
