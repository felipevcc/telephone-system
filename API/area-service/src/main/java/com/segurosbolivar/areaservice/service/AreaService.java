package com.segurosbolivar.areaservice.service;

import com.segurosbolivar.areaservice.dto.AreasPageDTO;
import com.segurosbolivar.areaservice.model.GeographicArea;

import java.util.List;

public interface AreaService {

    List<GeographicArea> getAllAreas();

    AreasPageDTO getAreasByCenterId(Long centerId, Integer page, Integer pageSize);
}
