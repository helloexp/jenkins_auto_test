package com.fastretailing.marketingpf.domain.enums;

import lombok.Getter;

public enum EXTRACTION_TARGET_ID {

    MAIL_ADDRESS(1, "Mail Address"), //メールアドレス
    ADID(2, "ADID"),
    IDFA(3, "IDFA"),
    ;

    @Getter
    private int value;

    @Getter
    private String name;

    EXTRACTION_TARGET_ID(int value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * convert extractionTargetId to enum by name
     *
     * @param name
     * @return EXTRACTION_TARGET_ID
     */
    public static EXTRACTION_TARGET_ID createFromName(String name) {
        if (name == null) {
            return null;
        }
        for (EXTRACTION_TARGET_ID extractionTarget : values()) {
            if (name.equals(extractionTarget.getName())) {
                return extractionTarget;
            }
        }
        return null;
    }

    /**
     * convert extractionTargetId to enum by value
     *
     * @param value
     * @return EXTRACTION_TARGET_ID
     */
    public static EXTRACTION_TARGET_ID createFromValue(String value) {
        if (value == null) {
            return null;
        }
        for (EXTRACTION_TARGET_ID extractionTarget : values()) {
            if (value.equals(extractionTarget.getValueAsString())) {
                return extractionTarget;
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
