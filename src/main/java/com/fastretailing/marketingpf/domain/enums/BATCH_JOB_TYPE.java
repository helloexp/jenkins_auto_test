package com.fastretailing.marketingpf.domain.enums;

import lombok.Getter;

public enum BATCH_JOB_TYPE {

    UPLOAD(1, "UPLOAD"),
    SEGMENT_BREAKDOWN(2, "SEGMENT_BREAKDOWN"),

    ;

    @Getter
    private int value;

    @Getter
    private String name;

    private BATCH_JOB_TYPE(int value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * convert batch job type to enum
     *
     * @param value
     * @return BATCH_JOB_TYPE
     */
    public static BATCH_JOB_TYPE createFromValue(String value) {
        if (value == null) {
            return null;
        }
        for (BATCH_JOB_TYPE batchJobType : values()) {
            if (value.equals(batchJobType.getValueAsString())) {
                return batchJobType;
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
