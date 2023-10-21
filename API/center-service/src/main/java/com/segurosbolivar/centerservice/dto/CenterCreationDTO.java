package com.segurosbolivar.centerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CenterCreationDTO {
    private String name;
    private String address;
    private String email;
    private String phoneNumber;
    private Integer initialNumber;
    private Integer finalNumber;
}
