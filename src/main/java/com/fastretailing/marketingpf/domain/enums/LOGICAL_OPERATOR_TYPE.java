package com.fastretailing.marketingpf.domain.enums;

import lombok.Getter;

public enum LOGICAL_OPERATOR_TYPE {

    AND(1, "AND"),
    OR(2, "OR");

    @Getter
    private int value;

    @Getter
    private String name;

    LOGICAL_OPERATOR_TYPE(int value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * Convert logical operator type to enum
     *
     * @param value
     * @return LOGICAL_OPERATOR_TYPE
     */
    public static LOGICAL_OPERATOR_TYPE createFromValue(String value) {
        if (value == null) {
            return null;
        }
        for (LOGICAL_OPERATOR_TYPE operatorType : values()) {
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
