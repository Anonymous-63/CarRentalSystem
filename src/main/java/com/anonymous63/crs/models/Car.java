package com.anonymous63.crs.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 20)
    private String brand;

    @Column(nullable = false, length = 20)
    private String model;

    @Column(nullable = false, length = 4)
    private Integer year;

    @Column(nullable = false, length = 20)
    private String color;

    @Column(length = 100)
    private String description;

    private String image;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Boolean enabled = true;
}
