package com.segurosbolivar.telephonenumberservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentTypeDTO {
    private Long documentTypeId;
    private String typeCode;
    private String description;
}
