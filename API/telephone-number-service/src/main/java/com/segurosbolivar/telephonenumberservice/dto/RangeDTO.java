package com.segurosbolivar.telephonenumberservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RangeDTO {
    private Integer initialNumber;
    private Integer finalNumber;
}
