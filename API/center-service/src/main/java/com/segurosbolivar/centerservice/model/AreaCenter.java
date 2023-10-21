package com.segurosbolivar.centerservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Table(name = "AREA_CENTER")
@IdClass(AreaCenterId.class)
public class AreaCenter {
    @Id
    @Column(name = "AREA_ID")
    private Long areaId;

    @Id
    @Column(name = "CENTER_ID")
    private Long centerId;
}
