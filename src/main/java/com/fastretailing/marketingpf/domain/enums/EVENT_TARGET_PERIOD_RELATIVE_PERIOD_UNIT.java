package com.fastretailing.marketingpf.domain.enums;

import lombok.Getter;

public enum EVENT_TARGET_PERIOD_RELATIVE_PERIOD_UNIT {

    YEAR(1, "Year", "YEAR"), //年
    DAY(2, "Day", "DAY"), //日
    MONTH(3, "Month", "MONTH"), //月
    WEEK(4, "Week", "WEEK"), //週
    ;

    @Getter
    private int value;

    @Getter
    private String name;

    @Getter
    private String bigqueryInterval;

    EVENT_TARGET_PERIOD_RELATIVE_PERIOD_UNIT(int value, String name, String bigqueryInterval) {
        this.value = value;
        this.name = name;
        this.bigqueryInterval = bigqueryInterval;
    }

    /**
     * Create from value
     *
     * @param value
     * @return EVENT_TARGET_PERIOD_RELATIVE_PERIOD_UNIT
     */
    public static EVENT_TARGET_PERIOD_RELATIVE_PERIOD_UNIT createFromValue(String value) {
        if (value == null) {
            return null;
        }
        for (EVENT_TARGET_PERIOD_RELATIVE_PERIOD_UNIT eventTargetPeriodRelativePeriodUnit : values()) {
            if (value.equals(eventTargetPeriodRelativePeriodUnit.getValueAsString())) {
                return eventTargetPeriodRelativePeriodUnit;
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
