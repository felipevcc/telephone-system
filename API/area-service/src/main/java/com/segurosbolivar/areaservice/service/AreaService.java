package com.segurosbolivar.areaservice.service;

import com.segurosbolivar.areaservice.dto.AreasPageDTO;
import com.segurosbolivar.areaservice.dto.GeographicAreaDTO;

import java.util.List;

public interface AreaService {

    GeographicAreaDTO getAreaById(Long areaId);

    List<GeographicAreaDTO> getAllAreas();

    AreasPageDTO getAreasByCenterId(Long centerId, Integer page, Integer pageSize);
}
