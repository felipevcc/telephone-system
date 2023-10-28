package com.segurosbolivar.telephonenumberservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NumberHistoryRowDTO {
    private String customerDocumentType;
    private String customerDocument;
    private Integer telephoneNumber;
    private Long centerId;
    private String assignmentDate;
    private String releaseDate;
}
