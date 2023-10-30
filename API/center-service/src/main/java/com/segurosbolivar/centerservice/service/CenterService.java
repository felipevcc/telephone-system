package com.segurosbolivar.centerservice.service;

import com.segurosbolivar.centerservice.dto.CenterCreationDTO;
import com.segurosbolivar.centerservice.dto.CenterUpdateDTO;
import com.segurosbolivar.centerservice.dto.CentersPageDTO;
import com.segurosbolivar.centerservice.model.Center;

import java.util.List;

public interface CenterService {

    CentersPageDTO getCentersByAreaId(Long areaId, Integer page, Integer pageSize);

    Center getCenterById(Long centerId);

    List<Center> getAllCentersByArea(Long areaId);

    Center createCenter(CenterCreationDTO newCenterData);

    Center updateCenter(Long centerId, CenterUpdateDTO centerData);
}
