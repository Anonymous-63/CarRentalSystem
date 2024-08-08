package com.anonymous63.crs.payloads.responses;

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
public class PageableResponse<T> {
    private List<T> entities;
    private Integer totalPages;
    private Long totalElements;
    private Integer noOfElements;
    private Integer currentPage;
    private Integer itemsPerPage;
    private Boolean isEmpty;
    private Boolean hasNext;
    private Boolean hasPrevious;
    private Boolean isFirst;
    private Boolean isLast;

}
