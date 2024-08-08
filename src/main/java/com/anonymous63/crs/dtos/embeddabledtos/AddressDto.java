package com.anonymous63.crs.dtos.embeddabledtos;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressDto {

    @Size(max = 20, message = "Street must be less than or equal to 20 characters")
    private String street;
    @Size(max = 20, message = "City must be less than or equal to 20 characters")
    private String city;
    @Size(max = 20, message = "State must be less than or equal to 20 characters")
    private String state;
    @Size(max = 20, message = "Country must be less than or equal to 20 characters")
    private String country;
    @Size(max = 6, message = "PinCode must be less than or equal to 6 characters")
    @Pattern(regexp = "\\d{6}", message = "Invalid pinCode. It must be a 6-digit number.")
    private Integer pinCode;
}
