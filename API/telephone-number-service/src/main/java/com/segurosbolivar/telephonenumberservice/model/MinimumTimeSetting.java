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
@Table(name = "MINIMUM_TIME_SETTING")
public class MinimumTimeSetting {
    @Id
    @Column("TIME_ID")
    private Long timeId;

    @Column("TIME_VALUE")
    private Integer timeValue;

    @Column("CREATED_AT")
    private LocalDateTime createdAt;
}
