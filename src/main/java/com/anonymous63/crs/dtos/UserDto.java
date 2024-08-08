package com.anonymous63.crs.dtos;

import com.anonymous63.crs.dtos.embeddabledtos.AddressDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
    private Long id;

    @NotBlank(message = "Username is mandatory")
    @Size(max = 20, message = "Username must be less than or equal to 20 characters")
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "First name is mandatory")
    @Size(max = 20, message = "First name must be less than or equal to 20 characters")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(max = 20, message = "Last name must be less than or equal to 20 characters")
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must be less than or equal to 100 characters")
    private String email;

    @NotBlank(message = "Mobile number is mandatory")
    @Size(max = 10, message = "Mobile number must be less than or equal to 10 characters")
    @Pattern(regexp = "\\d{10}", message = "Mobile number should be 10 digits")
    private String mobileNo;

    private AddressDto address;

    private boolean enabled = true;
}
