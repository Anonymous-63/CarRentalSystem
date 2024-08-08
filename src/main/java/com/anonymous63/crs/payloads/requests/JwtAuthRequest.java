package com.anonymous63.crs.payloads.requests;

import lombok.Data;

@Data
public class JwtAuthRequest {
    private String username;
    private String password;

    private String refreshToken;
}
