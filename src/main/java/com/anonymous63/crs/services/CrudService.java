package com.anonymous63.crs.services;

import com.anonymous63.crs.payloads.responses.PageableResponse;

import java.util.List;

public interface CrudService<T> {

    T save(T entity);
    T update(T entity);
    void delete(Long id);
    T findById(Long id);
    PageableResponse<T> findAll(Integer page, Integer size, String sortBy, String sortDir);
    List<T> disable(List<Long> ids);
    List<T> enable(List<Long> ids);
    String getEntityName();
}
