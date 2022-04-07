package com.fastretailing.marketingpf.domain.enums;

import lombok.Getter;

public enum SEGMENT_STATUS {

    TEMPORARY_REGISTRATION(1, "Temporary registration"), //仮登録
    SEGMENT_CREATION(2, "Segment creation"), //セグメント作成
    UPLOADED(3, "Uploaded"), //入稿済み
    ;

    @Getter
    private int value;

    @Getter
    private String name;

    SEGMENT_STATUS(int value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * Create from value
     *
     * @param value
     * @return SEGMENT_STATUS
     */
    public static SEGMENT_STATUS createFromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SEGMENT_STATUS segmentStatus : values()) {
            if (value.equals(segmentStatus.getValueAsString())) {
                return segmentStatus;
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
