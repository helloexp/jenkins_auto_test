package com.fastretailing.marketingpf.domain.enums;

import lombok.Getter;

public enum NUMBER_OF_PEOPLE_CONDITIONS {

    GREATER_OR_EQUAL(1, "greater or equal"),
    LESS_OR_EQUAL(2, "less or equal");

    @Getter
    private int value;

    @Getter
    private String name;

    NUMBER_OF_PEOPLE_CONDITIONS(int value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * Create enum from value
     *
     * @param value
     * @return NUMBER_OF_PEOPLE_CONDITIONS
     */
    public static NUMBER_OF_PEOPLE_CONDITIONS createFromValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (NUMBER_OF_PEOPLE_CONDITIONS condition : values()) {
            if (value.equals(condition.getValue())) {
                return condition;
            }
        }
        return null;
    }
}
