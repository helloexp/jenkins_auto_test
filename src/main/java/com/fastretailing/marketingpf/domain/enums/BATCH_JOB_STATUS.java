package com.fastretailing.marketingpf.domain.enums;

import lombok.Getter;

public enum BATCH_JOB_STATUS {

    REQUESTED(1, "REQUESTED"),
    PROCESSING(2, "PROCESSING"),
    SUCCESS(3, "SUCCESS"),
    STOP(4, "STOP"),
    ERROR(5, "ERROR"),

    ;

    @Getter
    private int value;

    @Getter
    private String name;

    BATCH_JOB_STATUS(int value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * convert status to enum
     *
     * @param value
     * @return STATUS
     */
    public static BATCH_JOB_STATUS create(String value) {
        if (value == null) {
            return null;
        }
        for (BATCH_JOB_STATUS status : values()) {
            if (value.equals(status.getValueAsString())) {
                return status;
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
