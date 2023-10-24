package com.segurosbolivar.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private Long customerId;
    private Long customerTypeId;
    private String name;
    private String lastName;
    private Date birthdate;
    private Long documentTypeId;
    private String document;
    private String address;
    private Long areaId;
    private String email;
    private String phoneNumber;
    private String telephoneNumber;
}
