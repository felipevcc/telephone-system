package com.segurosbolivar.customerservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor @NoArgsConstructor
@Table(name = "CUSTOMER_TYPE")
public class CustomerType {
    @Id
    @Column("CUSTOMER_TYPE_ID")
    private Long customerTypeId;

    @Column("CUSTOMER_TYPE_NAME")
    private String customerTypeName;
}
