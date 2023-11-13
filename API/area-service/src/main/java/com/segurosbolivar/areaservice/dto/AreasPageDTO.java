package com.segurosbolivar.areaservice.dto;

import com.segurosbolivar.areaservice.model.GeographicArea;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreasPageDTO {
    private Integer page;
    private Integer pageSize;
    private Long totalRecords;
    private Integer totalPages;
    private List<GeographicAreaDTO> geographicAreas;
}
