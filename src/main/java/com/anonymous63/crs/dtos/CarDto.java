package com.anonymous63.crs.dtos;

import lombok.Data;

@Data
public class CarDto {
    private Long id;
    private String name;
    private String model;
    private String brand;
    private String color;
    private String year;
    private String price;
    private String description;
    private String image;
    private String createdAt;
    private String updatedAt;
    private boolean enabled;
}
