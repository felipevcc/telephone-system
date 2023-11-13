package com.segurosbolivar.areaservice.controller;

import com.segurosbolivar.areaservice.dto.AreasPageDTO;
import com.segurosbolivar.areaservice.dto.GeographicAreaDTO;
import com.segurosbolivar.areaservice.service.AreaService;
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

    @GetMapping("/{areaId}")
    public ResponseEntity<GeographicAreaDTO> getAreaById(@PathVariable Long areaId) {
        GeographicAreaDTO foundArea = areaService.getAreaById(areaId);
        if (foundArea == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(foundArea);
    }

    @GetMapping
    public ResponseEntity<List<GeographicAreaDTO>> getAllAreas() {
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
