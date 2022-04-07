package com.fastretailing.marketingpf.domain.enums;

import lombok.Getter;

public enum ADS_PLATFORM {

    GOOGLE_ADS(1, "GOOGLE_ADS"),
    FACEBOOK_ADS(2, "FACEBOOK_ADS"),

    ;

    @Getter
    private int value;

    @Getter
    private String name;

    private ADS_PLATFORM(int value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * Create from value
     *
     * @param value
     * @return ADS_PLATFORM
     */
    public static ADS_PLATFORM create(int value) {
        for (ADS_PLATFORM e : values()) {
            if (value == e.value) {
                return e;
            }
        }
        return null;
    }
}
