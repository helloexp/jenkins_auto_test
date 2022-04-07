package com.fastretailing.marketingpf.domain.enums;

import lombok.Getter;

public enum OUTPUT_PLATFORM_TYPE {

    ADS_PF(1, "AdsPF"),
    CRM_PF(2, "CRMPF"),
    ;

    @Getter
    private int value;

    @Getter
    private String name;

    OUTPUT_PLATFORM_TYPE(int value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * convert output platform type to enum
     *
     * @param value
     * @return OUTPUT_PLATFORM_TYPE
     */
    public static OUTPUT_PLATFORM_TYPE createFromValue(String value) {
        if (value == null) {
            return null;
        }
        for (OUTPUT_PLATFORM_TYPE outputPlatformType : values()) {
            if (value.equals(outputPlatformType.getValueAsString())) {
                return outputPlatformType;
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
