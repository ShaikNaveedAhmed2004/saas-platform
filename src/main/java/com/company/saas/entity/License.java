package com.company.saas.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "licenses")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long appId;

    private String status;

    private LocalDate assignedDate;

    private LocalDate lastUsedDate;

}