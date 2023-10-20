package com.segurosbolivar.centerservice.controller;

import com.segurosbolivar.centerservice.dto.CentersPageDTO;
import com.segurosbolivar.centerservice.model.Center;
import com.segurosbolivar.centerservice.service.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Center> getCenterById(@PathVariable Long centerId) {
        Center foundCenter = centerService.getCenterById(centerId);
        if (foundCenter == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(foundCenter);
    }
}
