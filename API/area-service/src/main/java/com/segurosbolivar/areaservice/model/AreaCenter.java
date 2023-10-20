package com.segurosbolivar.areaservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Table(name = "AREA_CENTER")
public class AreaCenter {
    @Id
    @Column(name = "AREA_ID")
    private Long areaId;

    @Id
    @Column(name = "CENTER_ID")
    private Long centerId;
}
