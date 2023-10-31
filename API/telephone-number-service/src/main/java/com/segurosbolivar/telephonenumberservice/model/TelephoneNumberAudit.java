package com.segurosbolivar.telephonenumberservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor @NoArgsConstructor
@Table(name = "TELEPHONE_NUMBER_AUDIT")
public class TelephoneNumberAudit {
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

    @Column("RELEASE_DATE")
    private LocalDateTime releaseDate;

    @JsonIgnore
    @Column("IS_ACTIVE")
    private Integer isActive;
}
