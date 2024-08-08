package com.anonymous63.crs.controllers;

import com.anonymous63.crs.dtos.UserDto;
import com.anonymous63.crs.payloads.requests.JwtAuthRequest;
import com.anonymous63.crs.payloads.responses.JwtAuthResponse;
import com.anonymous63.crs.securities.JwtTokenHelper;
import com.anonymous63.crs.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtTokenHelper jwtTokenHelper;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthController(JwtTokenHelper jwtTokenHelper, UserDetailsService userDetailsService, UserService userService, AuthenticationManager authenticationManager) {
        this.jwtTokenHelper = jwtTokenHelper;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Create a token for the user
     * @param jwtAuthRequest object containing the username and password
     * @return the token
     */
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest) {
        this.authenticate(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());

        String token = this.jwtTokenHelper.generateToken(userDetails);
        String refreshToken = this.jwtTokenHelper.generateRefreshToken(userDetails);

        JwtAuthResponse jwtAuthResponse = JwtAuthResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    /**
     * Register a new user
     * @param userDto object containing the user details
     * @return the user details
     */
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
        UserDto newUser = this.userService.register(userDto);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    /**
     * Refresh the token
     * @param jwtAuthRequest jwtAuthRequest object containing the refresh token
     * @return the new token
     */
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthResponse> refreshToken(@RequestBody JwtAuthRequest jwtAuthRequest) {
        if (Boolean.TRUE.equals(this.jwtTokenHelper.isTokenExpired(jwtAuthRequest.getRefreshToken()))) {
            throw new BadCredentialsException("Invalid refresh token");
        }
        String username = this.jwtTokenHelper.getUsernameFromToken(jwtAuthRequest.getRefreshToken());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        String token = this.jwtTokenHelper.generateToken(userDetails);
        String newRefreshToken = this.jwtTokenHelper.generateRefreshToken(userDetails);

        JwtAuthResponse jwtAuthResponse = JwtAuthResponse.builder()
                .token(token)
                .refreshToken(newRefreshToken)
                .build();

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    /**
     * Authenticate the user
     * @param username username
     * @param password password
     */
    private void authenticate(String username, String password) {
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

}
