package com.anonymous63.crs.payloads.responses;

import com.anonymous63.crs.dtos.ErrorDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class APIResponse<T> {

    private boolean status;
    private String message;
    private T results;
    private List<ErrorDto> errors;

}
