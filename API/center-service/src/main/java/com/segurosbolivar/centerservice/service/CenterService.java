package com.segurosbolivar.centerservice.service;

import com.segurosbolivar.centerservice.dto.CenterCreationDTO;
import com.segurosbolivar.centerservice.dto.CenterDTO;
import com.segurosbolivar.centerservice.dto.CenterUpdateDTO;
import com.segurosbolivar.centerservice.dto.CentersPageDTO;

import java.util.List;

public interface CenterService {

    CentersPageDTO getCentersByAreaId(Long areaId, Integer page, Integer pageSize);

    CenterDTO getCenterById(Long centerId);

    List<CenterDTO> getAllCentersByArea(Long areaId);

    CenterDTO createCenter(CenterCreationDTO newCenterData);

    CenterDTO updateCenter(Long centerId, CenterUpdateDTO centerData);
}
