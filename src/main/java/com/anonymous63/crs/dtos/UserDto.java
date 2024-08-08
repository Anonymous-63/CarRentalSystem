package com.anonymous63.crs.dtos;

import com.anonymous63.crs.dtos.embeddabledtos.AddressDto;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNo;
    private AddressDto address;
    private boolean enabled;
}
