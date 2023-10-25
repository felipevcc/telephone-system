package com.segurosbolivar.customerservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor @NoArgsConstructor
@Table(name = "CUSTOMER")
public class Customer {
    @Id
    @Column("CUSTOMER_ID")
    private Long customerId;

    @Column("CUSTOMER_TYPE_ID")
    private Long customerTypeId;

    @Column("NAME")
    private String name;

    @Column("LAST_NAME")
    private String lastName;

    @Column("BIRTHDATE")
    private LocalDateTime birthdate;

    @Column("DOCUMENT_TYPE_ID")
    private Long documentTypeId;

    @Column("DOCUMENT")
    private String document;

    @Column("ADDRESS")
    private String address;

    @Column("AREA_ID")
    private Long areaId;

    @Column("EMAIL")
    private String email;

    @Column("PHONE_NUMBER")
    private String phoneNumber;

    @JsonIgnore
    @Column("CREATED_AT")
    private LocalDateTime createdAt;
}
