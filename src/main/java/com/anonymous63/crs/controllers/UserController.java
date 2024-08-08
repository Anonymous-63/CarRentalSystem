package com.anonymous63.crs.controllers;

import com.anonymous63.crs.dtos.UserDto;
import com.anonymous63.crs.payloads.responses.APIResponse;
import com.anonymous63.crs.payloads.responses.PageableResponse;
import com.anonymous63.crs.services.UserService;
import com.anonymous63.crs.utils.Constants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<APIResponse<UserDto>> createUser(@RequestBody @Valid UserDto userDTO) {
        UserDto saveUser = this.userService.save(userDTO);
        APIResponse<UserDto> response = APIResponse.<UserDto>builder()
                .status(true)
                .message("User created successfully")
                .results(saveUser)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<APIResponse<UserDto>> getUser(@PathVariable Long id) {
        UserDto userDTO = this.userService.findById(id);
        APIResponse<UserDto> response = APIResponse.<UserDto>builder()
                .status(true)
                .message("User fetched successfully")
                .results(userDTO)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<APIResponse<PageableResponse<UserDto>>> findAll(
            @RequestParam(value = "page", defaultValue = Constants.PAGE_NUMBER, required = false) int page,
            @RequestParam(value = "size", defaultValue = Constants.PAGE_SIZE, required = false) int size,
            @RequestParam(value = "sortBy", defaultValue = Constants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = Constants.SORT_DIR, required = false) String sortDir
    ) {
        PageableResponse<UserDto> pageableResponse = this.userService.findAll(page, size, sortBy, sortDir);
        APIResponse<PageableResponse<UserDto>> response = APIResponse.<PageableResponse<UserDto>>builder()
                .status(true)
                .message("Users fetched successfully")
                .results(pageableResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
