package com.segurosbolivar.centerservice.service;

import com.segurosbolivar.centerservice.dto.CenterCreationDTO;
import com.segurosbolivar.centerservice.dto.CentersPageDTO;
import com.segurosbolivar.centerservice.model.AreaCenter;
import com.segurosbolivar.centerservice.model.Center;
import com.segurosbolivar.centerservice.repository.AreaCenterRepository;
import com.segurosbolivar.centerservice.repository.CenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CenterServiceImp implements CenterService {

    @Autowired
    CenterRepository centerRepository;

    @Autowired
    AreaCenterRepository areaCenterRepository;

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
            newCenterId = centerRepository.createCenter(
                    newCenterData.getName(),
                    newCenterData.getAddress(),
                    newCenterData.getEmail(),
                    newCenterData.getPhoneNumber(),
                    newCenterData.getInitialNumber(),
                    newCenterData.getFinalNumber()
            );
            if (newCenterId == null) {
                return null;
            }

            for (Long areaId:newCenterData.getGeographicAreasIds()) {
                if (areaCenterRepository.findAreaById(areaId) == null) {
                    continue;
                }
                AreaCenter newAreaCenter = new AreaCenter();
                newAreaCenter.setAreaId(areaId);
                newAreaCenter.setCenterId(newCenterId);
                areaCenterRepository.save(newAreaCenter);
            }

            return getCenterById(newCenterId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
