package com.segurosbolivar.telephonenumberservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelephoneNumberDTO {
    private Long numberRecordId;
    private Long centerId;
    private Long customerId;
    private Integer phoneNumber;
    private LocalDateTime assignmentDate;
    private LocalDateTime releaseDate;
    private Integer isActive;
}
