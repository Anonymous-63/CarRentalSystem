package com.anonymous63.crs.dtos;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CarDto {
    @Length(max = 20, message = "Name must be between 3 and 20 characters")
    private Long id;
    private String name;
    private String model;
    private String brand;
    private String color;
    private Integer year;
    private String price;
    private String description;
    private String image;
    private String createdAt;
    private String updatedAt;
    private Boolean enabled;
}
