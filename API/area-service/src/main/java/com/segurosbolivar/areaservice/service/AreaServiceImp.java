package com.segurosbolivar.areaservice.service;

import com.segurosbolivar.areaservice.dto.AreasPageDTO;
import com.segurosbolivar.areaservice.dto.GeographicAreaDTO;
import com.segurosbolivar.areaservice.mapper.GeographicAreaMapper;
import com.segurosbolivar.areaservice.model.GeographicArea;
import com.segurosbolivar.areaservice.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AreaServiceImp implements AreaService {

    @Autowired
    AreaRepository areaRepository;

    @Autowired
    GeographicAreaMapper areaMapper;

    @Override
    public GeographicAreaDTO getAreaById(Long areaId) {
        GeographicArea foundArea = areaRepository.findById(areaId).orElse(null);
        if (foundArea == null) {
            return null;
        }
        return areaMapper.geographicAreaToDTO(foundArea);
    }

    @Override
    public List<GeographicAreaDTO> getAllAreas() {
        List<GeographicArea> foundAreas = areaRepository.findAllByOrderByAreaId();
        return foundAreas.stream()
                .map(area -> areaMapper.geographicAreaToDTO(area))
                .collect(Collectors.toList());
    }

    @Override
    public AreasPageDTO getAreasByCenterId(Long centerId, Integer page, Integer pageSize) {
        AreasPageDTO pagedAreasResponse = new AreasPageDTO();

        Long totalRecords = areaRepository.countAreas(centerId);
        Integer totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        Pageable pageable = PageRequest.of(page, pageSize);
        List<GeographicArea> areas = areaRepository.findAreasByCenter(centerId, pageable);

        List<GeographicAreaDTO> mappedAreas = areas.stream()
                .map(area -> areaMapper.geographicAreaToDTO(area))
                .collect(Collectors.toList());

        pagedAreasResponse.setPage(page);
        pagedAreasResponse.setPageSize(pageSize);
        pagedAreasResponse.setTotalRecords(totalRecords);
        pagedAreasResponse.setTotalPages(totalPages);
        pagedAreasResponse.setGeographicAreas(mappedAreas);

        return pagedAreasResponse;
    }
}
