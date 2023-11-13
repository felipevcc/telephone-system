package com.segurosbolivar.areaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeographicAreaDTO {
    private Long areaId;
    private String name;
    private Integer code;
    private Integer commune;
}
