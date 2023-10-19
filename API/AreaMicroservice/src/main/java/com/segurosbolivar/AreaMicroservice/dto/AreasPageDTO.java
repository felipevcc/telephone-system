package com.segurosbolivar.AreaMicroservice.dto;

import com.segurosbolivar.AreaMicroservice.model.GeographicArea;
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
    private List<GeographicArea> geographicAreas;
}
