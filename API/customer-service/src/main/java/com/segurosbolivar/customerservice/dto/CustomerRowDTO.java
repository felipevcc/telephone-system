package com.segurosbolivar.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO for customer rows in CSV file
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRowDTO {
    private Long customerTypeId;
    private String name;
    private String lastName;
    private String birthdate;
    private Long documentTypeId;
    private String document;
    private String address;
    private Long areaCode;
    private String email;
    private String phoneNumber;
}
