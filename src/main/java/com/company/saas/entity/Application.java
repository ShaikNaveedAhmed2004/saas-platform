package com.company.saas.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "applications")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String appName;

    private String vendor;

    private int licenseCount;

    private double costPerLicense;

}