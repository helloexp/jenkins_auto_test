package com.fastretailing.marketingpf.domain.enums;

import lombok.Getter;

public enum OPERATOR_TYPE {

    EQUAL(1, "="),
    NOT_EQUAL(2, "!="),
    IN(3, "IN"),
    NOT_IN(4, "NOT IN"),
    LIKE(5, "LIKE"),
    NOT_LIKE(6, "NOT LIKE"),
    GREATER_OR_EQUAL(7, ">="),
    GREATER(8, ">"),
    LESS_OR_EQUAL(9, "<="),
    LESS(10, "<");

    @Getter
    private int value;

    @Getter
    private String name;

    OPERATOR_TYPE(int value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * Convert operator type to enum
     *
     * @param value
     * @return OPERATOR_TYPE
     */
    public static OPERATOR_TYPE createFromValue(String value) {
        if (value == null) {
            return null;
        }
        for (OPERATOR_TYPE operatorType : values()) {
            if (value.equals(operatorType.getValueAsString())) {
                return operatorType;
            }
        }
        return null;
    }

    /**
     * Get value of enum as String
     *
     * @return String
     */
    public String getValueAsString() {
        return String.valueOf(this.value);
    }
}
