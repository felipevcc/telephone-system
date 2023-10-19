package com.segurosbolivar.AreaMicroservice.service;

import com.segurosbolivar.AreaMicroservice.dto.AreasPageDTO;
import com.segurosbolivar.AreaMicroservice.model.GeographicArea;
import com.segurosbolivar.AreaMicroservice.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImp implements AreaService {

    @Autowired
    AreaRepository areaRepository;

    @Override
    public List<GeographicArea> getAllAreas() {
        return areaRepository.findAllByOrderByAreaId();
    };

    @Override
    public AreasPageDTO getAreasByCenterId(Long centerId, Integer page, Integer pageSize) {
        AreasPageDTO pagedAreasResponse = new AreasPageDTO();

        Long totalRecords = areaRepository.countAreas(centerId);
        Integer totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        Pageable pageable = PageRequest.of(page, pageSize);
        List<GeographicArea> areas = areaRepository.findAreasByCenter(centerId, pageable);

        pagedAreasResponse.setPage(page);
        pagedAreasResponse.setPageSize(pageSize);
        pagedAreasResponse.setTotalRecords(totalRecords);
        pagedAreasResponse.setTotalPages(totalPages);
        pagedAreasResponse.setGeographicAreas(areas);

        return pagedAreasResponse;
    };
}
