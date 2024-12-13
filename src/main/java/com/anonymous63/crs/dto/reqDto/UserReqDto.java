package com.anonymous63.crs.dto.reqDto;

import lombok.Data;

@Data
public class UserReqDto {
    private Long id;
    private String userName;
    private String firstName;
    private String middeleName;
    private String lastName;
    private String email;
    private String phoneNo;
    // private Address address;
    private String password;
}
