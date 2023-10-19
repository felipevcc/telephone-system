package com.segurosbolivar.AreaMicroservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Table(name = "GEOGRAPHIC_AREA")
public class GeographicArea {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "areaIdGen")
    @SequenceGenerator(name = "areaIdGen", sequenceName = "SEQ_GEOGRAPHIC_AREA", allocationSize = 1)
    @Column(name = "AREA_ID")
    private Long areaId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CODE")
    private Integer code;

    @Column(name = "COMMUNE")
    private Integer commune;
}
