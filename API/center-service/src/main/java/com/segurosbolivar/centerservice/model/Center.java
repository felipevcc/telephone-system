package com.segurosbolivar.centerservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Table(name = "CENTER")
public class Center {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "centerIdGen")
    @SequenceGenerator(name = "centerIdGen", sequenceName = "SEQ_CENTER", allocationSize = 1)
    @Column(name = "CENTER_ID")
    private Long centerId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "INITIAL_NUMBER")
    private Integer initialNumber;

    @Column(name = "FINAL_NUMBER")
    private Integer finalNumber;

    @JsonIgnore
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
}
