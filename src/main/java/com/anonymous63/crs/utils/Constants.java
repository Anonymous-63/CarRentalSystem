package com.anonymous63.crs.utils;

import lombok.Getter;

public class Constants {
    public static final String PAGE_NUMBER = "0";
    public static final String PAGE_SIZE = "100";
    public static final String SORT_BY = "id";
    public static final String SORT_DIR = "ASC";

    public static final Long ROLE_ADMIN = 1L;
    public static final Long ROLE_USER = 2L;

    @Getter
    public enum Operation {
        SAVE(1, "Save"),
        UPDATE(2, "Update"),
        DELETE(3, "Delete"),
        FIND_BY_ID(4, "FindById"),
        FIND_ALL(5, "FindAll"),
        DISABLE(6, "Disable"),
        ENABLE(7, "Enable");

        private final Integer operationId;
        private final String operationName;

        Operation(int operationId, String operationName) {
            this.operationId = operationId;
            this.operationName = operationName;
        }

        public static Operation fromOperationId(int operationId) {
            for (Operation operation : Operation.values()) {
                if (operation.getOperationId() == operationId) {
                    return operation;
                }
            }
            throw new IllegalArgumentException("Invalid operationId: " + operationId);
        }
    }

}
