package com.segurosbolivar.customerservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor @NoArgsConstructor
@Table(name = "DOCUMENT_TYPE")
public class DocumentType {
    @Id
    @Column("DOCUMENT_TYPE_ID")
    private Long documentTypeId;

    @Column("TYPE_CODE")
    private String typeCode;

    @Column("DESCRIPTION")
    private String description;
}
