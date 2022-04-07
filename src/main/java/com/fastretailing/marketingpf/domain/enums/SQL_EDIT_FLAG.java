package com.fastretailing.marketingpf.domain.enums;

import lombok.Getter;

public enum SQL_EDIT_FLAG {

    BASIC_SEGMENT("0", "Basic Segment"),
    SQL_SEGMENT("1", "Sql Segment")
    ;

    @Getter
    private String value;

    @Getter
    private String name;

    SQL_EDIT_FLAG(String value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * Create from value
     *
     * @param value
     * @return SQL_EDIT_FLAG
     */
    public static SQL_EDIT_FLAG createFromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SQL_EDIT_FLAG sqlEditFlag : values()) {
            if (value.equals(sqlEditFlag.getValueAsString())) {
                return sqlEditFlag;
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
