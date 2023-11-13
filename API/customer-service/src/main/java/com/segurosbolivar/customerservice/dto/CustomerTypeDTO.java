package com.segurosbolivar.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerTypeDTO {
    private Long customerTypeId;
    private String customerTypeName;
}
