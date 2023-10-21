package com.segurosbolivar.centerservice.service;

import com.segurosbolivar.centerservice.dto.CenterCreationDTO;
import com.segurosbolivar.centerservice.dto.CentersPageDTO;
import com.segurosbolivar.centerservice.model.Center;

public interface CenterService {

    CentersPageDTO getCentersByAreaId(Long areaId, Integer page, Integer pageSize);

    Center getCenterById(Long centerId);

    Center createCenter(CenterCreationDTO newCenterData);
}
