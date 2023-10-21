package com.segurosbolivar.centerservice.service;

import com.segurosbolivar.centerservice.dto.CenterCreationDTO;
import com.segurosbolivar.centerservice.dto.CentersPageDTO;
import com.segurosbolivar.centerservice.model.Center;
import com.segurosbolivar.centerservice.repository.CenterProceduresRepository;
import com.segurosbolivar.centerservice.repository.CenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class CenterServiceImp implements CenterService {

    @Autowired
    CenterRepository centerRepository;

    @Autowired
    CenterProceduresRepository centerProcsRepository;

    @Override
    public CentersPageDTO getCentersByAreaId(Long areaId, Integer page, Integer pageSize) {
        CentersPageDTO pagedCentersResponse = new CentersPageDTO();

        Long totalRecords = centerRepository.countCenters(areaId);
        Integer totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        Pageable pageable = PageRequest.of(page, pageSize);
        List<Center> centers = centerRepository.findCentersByArea(areaId, pageable);

        pagedCentersResponse.setPage(page);
        pagedCentersResponse.setPageSize(pageSize);
        pagedCentersResponse.setTotalRecords(totalRecords);
        pagedCentersResponse.setTotalPages(totalPages);
        pagedCentersResponse.setCenters(centers);

        return pagedCentersResponse;
    }

    @Override
    public Center getCenterById(Long centerId) {
        return centerRepository.findById(centerId).orElse(null);
    }

    @Override
    public Center createCenter(CenterCreationDTO newCenterData) {
        Long newCenterId = null;
        try {
            newCenterId = centerProcsRepository.createCenter(newCenterData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (newCenterId == null) {
            return null;
        }
        return getCenterById(newCenterId);
    }
}