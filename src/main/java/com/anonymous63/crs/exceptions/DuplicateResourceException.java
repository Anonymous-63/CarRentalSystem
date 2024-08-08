package com.anonymous63.crs.exceptions;

public class DuplicateResourceException extends RuntimeException {
    final String entityName;
    final String fieldName;
    final transient Object fieldValue;

    public DuplicateResourceException(String entityName, String fieldName, Object fieldValue) {
        super(String.format("%s already exists with %s : '%s'", entityName, fieldName, fieldValue));
        this.entityName = entityName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
