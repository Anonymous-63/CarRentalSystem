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

import java.util.List;

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

    @PutMapping("/user")
    public ResponseEntity<APIResponse<UserDto>> updateUser(@RequestBody @Valid UserDto userDTO) {
        UserDto updateUser = this.userService.update(userDTO);
        APIResponse<UserDto> response = APIResponse.<UserDto>builder()
                .status(true)
                .message("User updated successfully")
                .results(updateUser)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<APIResponse<String>> deleteUser(@PathVariable Long id) {
        this.userService.delete(id);
        APIResponse<String> response = APIResponse.<String>builder()
                .status(true)
                .message("User deleted successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<APIResponse<UserDto>> findUserById(@PathVariable Long id) {
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
    public ResponseEntity<APIResponse<PageableResponse<UserDto>>> findAllUsers(
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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/user/disable")
    public ResponseEntity<APIResponse<List<UserDto>>> disableUsers(@RequestBody @Valid List<Long> ids) {
        List<UserDto> disableUsers = this.userService.disable(ids);
        APIResponse<List<UserDto>> response = APIResponse.<List<UserDto>>builder()
                .status(true)
                .message("Users disabled successfully")
                .results(disableUsers)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/user/enable")
    public ResponseEntity<APIResponse<List<UserDto>>> enableUsers(@RequestBody @Valid List<Long> ids) {
        List<UserDto> enableUsers = this.userService.enable(ids);
        APIResponse<List<UserDto>> response = APIResponse.<List<UserDto>>builder()
                .status(true)
                .message("Users enabled successfully")
                .results(enableUsers)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
