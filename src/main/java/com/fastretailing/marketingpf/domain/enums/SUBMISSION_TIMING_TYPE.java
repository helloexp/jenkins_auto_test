package com.fastretailing.marketingpf.domain.enums;

import lombok.Getter;

public enum SUBMISSION_TIMING_TYPE {

    IMMEDIATE_SUBMISSION(1, "Immediate submission"), //即時入稿
    RESERVATION_SUBMISSION(2, "Reservation submission"), //予約入稿
    REGULAR_SUBMISSION(3, "Regular submission"), //定期入稿
    ;

    @Getter
    private int value;

    @Getter
    private String name;

    SUBMISSION_TIMING_TYPE(int value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * convert submission timing type to enum
     *
     * @param value
     * @return SUBMISSION_TIMING_TYPE
     */
    public static SUBMISSION_TIMING_TYPE createFromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SUBMISSION_TIMING_TYPE submissionTimingType : values()) {
            if (value.equals(submissionTimingType.getValueAsString())) {
                return submissionTimingType;
            }
        }
        return null;
    }

    /**
     * get value as String
     *
     * @return String
     */
    public String getValueAsString() {
        return String.valueOf(this.value);
    }
}
