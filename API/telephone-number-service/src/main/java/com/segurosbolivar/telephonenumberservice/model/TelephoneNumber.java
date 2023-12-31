package com.segurosbolivar.telephonenumberservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor @NoArgsConstructor
@Table(name = "TELEPHONE_NUMBER")
public class TelephoneNumber {
    @Id
    @Column("NUMBER_RECORD_ID")
    private Long numberRecordId;

    @Column("CENTER_ID")
    private Long centerId;

    @Column("CUSTOMER_ID")
    private Long customerId;

    @Column("PHONE_NUMBER")
    private Integer phoneNumber;

    @Column("ASSIGNMENT_DATE")
    private LocalDateTime assignmentDate;
}
