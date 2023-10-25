package com.segurosbolivar.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreationDTO {
    private Long customerTypeId;
    private String name;
    private String lastName;
    private String birthdate;
    private Long documentTypeId;
    private String document;
    private String address;
    private Long areaId;
    private String email;
    private String phoneNumber;
}
