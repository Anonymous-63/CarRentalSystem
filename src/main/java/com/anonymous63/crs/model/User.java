package com.anonymous63.crs.model;

import java.time.LocalDateTime;

public class User {
    private Long id;
    private String userName;
    private String firstName;
    private String middeleName;
    private String lastName;
    private String email;
    private String phoneNo;
    private Address address;
    private String password;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
