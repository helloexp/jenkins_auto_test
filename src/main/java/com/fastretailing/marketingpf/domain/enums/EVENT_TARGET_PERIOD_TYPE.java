package com.fastretailing.marketingpf.domain.enums;

import lombok.Getter;

public enum EVENT_TARGET_PERIOD_TYPE {

    ABSOLUTE_DATE(1, "Absolute date"), //絶対日付
    RELATIVE_DATE(2, "Relative date"), //相対日付
    ;

    @Getter
    private int value;

    @Getter
    private String name;

    EVENT_TARGET_PERIOD_TYPE(int value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * Create from value
     *
     * @param value
     * @return EVENT_TARGET_PERIOD_TYPE
     */
    public static EVENT_TARGET_PERIOD_TYPE createFromValue(String value) {
        if (value == null) {
            return null;
        }
        for (EVENT_TARGET_PERIOD_TYPE eventTargetPeriodType : values()) {
            if (value.equals(eventTargetPeriodType.getValueAsString())) {
                return eventTargetPeriodType;
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
