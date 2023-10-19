package com.segurosbolivar.AreaMicroservice.service;

import com.segurosbolivar.AreaMicroservice.dto.AreasPageDTO;
import com.segurosbolivar.AreaMicroservice.model.GeographicArea;

import java.util.List;

public interface AreaService {

    List<GeographicArea> getAllAreas();

    AreasPageDTO getAreasByCenterId(Long centerId, Integer page, Integer pageSize);
}
