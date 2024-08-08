package com.anonymous63.crs.exceptions.handler;

import com.anonymous63.crs.dtos.ErrorDto;
import com.anonymous63.crs.exceptions.DuplicateResourceException;
import com.anonymous63.crs.exceptions.ResourceNotFoundException;
import com.anonymous63.crs.payloads.responses.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle MethodArgumentNotValidException
     * @param ex MethodArgumentNotValidException
     * @return APIResponse
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIResponse<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ErrorDto> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> {
            ErrorDto error = new ErrorDto(e.getField(), e.getDefaultMessage());
            errors.add(error);
        });

        return APIResponse.<ErrorDto>builder()
                .status(false)
                .message("Validation Error")
                .errors(errors)
                .build();
    }

    /**
     * Handle ResourceNotFoundException
     * @param ex ResourceNotFoundException
     * @return APIResponse
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public APIResponse<ErrorDto> handleResourceNotFoundException(ResourceNotFoundException ex) {

        return APIResponse.<ErrorDto>builder()
                .status(false)
                .errors(Collections.singletonList(new ErrorDto("", ex.getMessage())))
                .build();
    }

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIResponse<ErrorDto> handleDuplicateResourceException(DuplicateResourceException ex) {
        return APIResponse.<ErrorDto>builder()
                .status(false)
                .errors(Collections.singletonList(new ErrorDto("", ex.getMessage())))
                .build();
    }

    /**
     * Handle RuntimeException
     * @param ex RuntimeException
     * @return APIResponse
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public APIResponse<ErrorDto> handleRuntimeException(RuntimeException ex) {
        return APIResponse.<ErrorDto>builder()
                .status(false)
                .errors(Collections.singletonList(new ErrorDto("", ex.getMessage())))
                .build();
    }

    /**
     * Handle BadCredentialsException
     * @param ex BadCredentialsException
     * @return APIResponse
     */
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public APIResponse<ErrorDto> handleBadCredentialsException(BadCredentialsException ex) {
        return APIResponse.<ErrorDto>builder()
                .status(false)
                .errors(Collections.singletonList(new ErrorDto("", ex.getMessage())))
                .build();
    }

}
