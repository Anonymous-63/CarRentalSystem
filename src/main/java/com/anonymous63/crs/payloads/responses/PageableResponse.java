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
    private int totalPages;
    private long totalElements;
    private int noOfElements;
    private int currentPage;
    private int itemsPerPage;
    private boolean isEmpty;
    private boolean hasNext;
    private boolean hasPrevious;
    private boolean isFirst;
    private boolean isLast;

}
