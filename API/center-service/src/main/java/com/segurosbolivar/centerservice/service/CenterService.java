package com.segurosbolivar.centerservice.service;

import com.segurosbolivar.centerservice.dto.CentersPageDTO;

public interface CenterService {
    CentersPageDTO getCentersByAreaId(Long areaId, Integer page, Integer pageSize);
}
