package com.segurosbolivar.centerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CentersPageDTO {
    private Integer page;
    private Integer pageSize;
    private Long totalRecords;
    private Integer totalPages;
    private List<CenterDTO> centers;
}
