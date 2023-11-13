package com.segurosbolivar.centerservice.controller;

import com.segurosbolivar.centerservice.dto.CenterCreationDTO;
import com.segurosbolivar.centerservice.dto.CenterDTO;
import com.segurosbolivar.centerservice.dto.CenterUpdateDTO;
import com.segurosbolivar.centerservice.dto.CentersPageDTO;
import com.segurosbolivar.centerservice.service.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/center")
@CrossOrigin
public class CenterController {

    @Autowired
    CenterService centerService;

    @GetMapping("/paged")
    public ResponseEntity<CentersPageDTO> getCentersByAreaId(
            @RequestParam("areaId") Long areaId,
            @RequestParam("page") Integer page,
            @RequestParam("pageSize") Integer pageSize
    ) {
        CentersPageDTO pagedCentersResponse = centerService.getCentersByAreaId(areaId, page, pageSize);
        if (pagedCentersResponse.getCenters().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(pagedCentersResponse);
    }

    @GetMapping("/{centerId}")
    public ResponseEntity<CenterDTO> getCenterById(@PathVariable Long centerId) {
        CenterDTO foundCenter = centerService.getCenterById(centerId);
        if (foundCenter == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(foundCenter);
    }

    @GetMapping("/area/{areaId}")
    public ResponseEntity<List<CenterDTO>> getAllCentersByArea(@PathVariable Long areaId) {
        return ResponseEntity.status(HttpStatus.OK).body(centerService.getAllCentersByArea(areaId));
    }

    @PostMapping
    public ResponseEntity<CenterDTO> createCenter(@RequestBody CenterCreationDTO newCenterData) {
        CenterDTO newCenter = centerService.createCenter(newCenterData);
        if (newCenter == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(newCenter);
    }

    @PutMapping("/{centerId}")
    public ResponseEntity<CenterDTO> updateCenter(@PathVariable Long centerId, @RequestBody CenterUpdateDTO centerData) {
        CenterDTO updatedCenter = centerService.updateCenter(centerId, centerData);
        if (updatedCenter == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedCenter);
    }
}
