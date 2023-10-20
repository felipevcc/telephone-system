package com.segurosbolivar.AreaMicroservice.controller;

import com.segurosbolivar.AreaMicroservice.dto.AreasPageDTO;
import com.segurosbolivar.AreaMicroservice.model.GeographicArea;
import com.segurosbolivar.AreaMicroservice.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/area")
@CrossOrigin
public class AreaController {

    @Autowired
    AreaService areaService;

    @GetMapping
    public ResponseEntity<List<GeographicArea>> getAllAreas() {
        return ResponseEntity.status(HttpStatus.OK).body(areaService.getAllAreas());
    }

    @GetMapping("/paged")
    public ResponseEntity<AreasPageDTO> getAreasByCenterId(
            @RequestParam("centerId") Long centerId,
            @RequestParam("page") Integer page,
            @RequestParam("pageSize") Integer pageSize
    ) {
        AreasPageDTO pagedAreasResponse = areaService.getAreasByCenterId(centerId, page, pageSize);
        if (pagedAreasResponse.getGeographicAreas().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(pagedAreasResponse);
    }
}
