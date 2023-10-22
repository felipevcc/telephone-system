package com.segurosbolivar.centerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CenterUpdateDTO {
    private String address;
    private String email;
    private String phoneNumber;
}
