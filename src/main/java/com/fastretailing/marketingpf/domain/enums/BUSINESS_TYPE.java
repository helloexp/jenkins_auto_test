package com.fastretailing.marketingpf.domain.enums;

import lombok.Getter;

public enum BUSINESS_TYPE {

    ATTRACTING_EXTERNAL_CUSTOMER(1, "Attracting external customer"), //外部集客
    CRM(2, "CRM"),
    ;

    @Getter
    private int value;

    @Getter
    private String name;

    BUSINESS_TYPE(int value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * convert business type to enum
     *
     * @param value
     * @return BUSINESS_TYPE
     */
    public static BUSINESS_TYPE createFromValue(String value) {
        if (value == null) {
            return null;
        }
        for (BUSINESS_TYPE businessType : values()) {
            if (value.equals(businessType.getValueAsString())) {
                return businessType;
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
