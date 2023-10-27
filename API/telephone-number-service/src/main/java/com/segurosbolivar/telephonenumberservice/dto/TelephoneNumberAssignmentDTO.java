package com.segurosbolivar.telephonenumberservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelephoneNumberAssignmentDTO {
    private Long centerId;
    private Long customerId;
    private Integer phoneNumber;
}
