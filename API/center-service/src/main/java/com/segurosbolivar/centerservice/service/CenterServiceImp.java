package com.segurosbolivar.centerservice.service;

import com.segurosbolivar.centerservice.dto.*;
import com.segurosbolivar.centerservice.mapper.CenterMapper;
import com.segurosbolivar.centerservice.model.AreaCenter;
import com.segurosbolivar.centerservice.model.Center;
import com.segurosbolivar.centerservice.repository.AreaCenterRepository;
import com.segurosbolivar.centerservice.repository.CenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CenterServiceImp implements CenterService {

    @Autowired
    CenterRepository centerRepository;

    @Autowired
    AreaCenterRepository areaCenterRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CenterMapper centerMapper;

    @Override
    public CentersPageDTO getCentersByAreaId(Long areaId, Integer page, Integer pageSize) {
        CentersPageDTO pagedCentersResponse = new CentersPageDTO();

        Long totalRecords = centerRepository.countCenters(areaId);
        Integer totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        Pageable pageable = PageRequest.of(page, pageSize);
        List<Center> centers = centerRepository.findCentersByArea(areaId, pageable);

        List<CenterDTO> mappedCenters = centers.stream()
                .map(center -> centerMapper.centerToDTO(center))
                .collect(Collectors.toList());

        pagedCentersResponse.setPage(page);
        pagedCentersResponse.setPageSize(pageSize);
        pagedCentersResponse.setTotalRecords(totalRecords);
        pagedCentersResponse.setTotalPages(totalPages);
        pagedCentersResponse.setCenters(mappedCenters);

        return pagedCentersResponse;
    }

    @Override
    public CenterDTO getCenterById(Long centerId) {
        Center foundCenter = centerRepository.findById(centerId).orElse(null);
        if (foundCenter == null) {
            return null;
        }
        return centerMapper.centerToDTO(foundCenter);
    }

    @Override
    public List<CenterDTO> getAllCentersByArea(Long areaId) {
        List<Center> foundCenters = centerRepository.findAllCentersByArea(areaId);
        return foundCenters.stream()
                .map(center -> centerMapper.centerToDTO(center))
                .collect(Collectors.toList());
    }

    @Override
    public CenterDTO createCenter(CenterCreationDTO newCenterData) {
        Long newCenterId = null;
        try {
            int lengthInitialNumber = String.valueOf(newCenterData.getInitialNumber()).length();
            int lengthFinalNumber = String.valueOf(newCenterData.getFinalNumber()).length();
            if (newCenterData.getGeographicAreasIds().isEmpty()
                || lengthInitialNumber < 7 || lengthFinalNumber > 8
                || newCenterData.getFinalNumber() - newCenterData.getInitialNumber() + 1 < 10000000) {
                return null;
            }

            List<Long> validAreas = new ArrayList<>();
            for (Long areaId:newCenterData.getGeographicAreasIds()) {
                if (!areaExistsById(areaId) || validAreas.contains(areaId)) {
                    continue;
                }
                validAreas.add(areaId);
            }
            if (validAreas.isEmpty()) {
                return null;
            }

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

            for (Long areaId:validAreas) {
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

    @Override
    public CenterDTO updateCenter(Long centerId, CenterUpdateDTO centerData) {
        try {
            if (!centerRepository.existsById(centerId)) {
                return null;
            }
            centerRepository.updateCenter(
                    centerId,
                    centerData.getAddress(),
                    centerData.getEmail(),
                    centerData.getPhoneNumber()
            );
            return getCenterById(centerId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean areaExistsById(Long areaId) {
        try {
            ResponseEntity<GeographicAreaDTO> response = restTemplate.exchange(
                    "http://GEOGRAPHIC-AREA-SERVICE/api/v1/area/{areaId}",
                    HttpMethod.GET,
                    null,
                    GeographicAreaDTO.class,
                    areaId
            );
            return response.getStatusCode() == HttpStatus.OK;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }
}
