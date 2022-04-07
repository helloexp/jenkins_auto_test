package com.fastretailing.marketingpf.domain.enums;

import lombok.Getter;

public enum COUNTRY {

    JP("1", "JP"),

    ;

    @Getter
    private String code;

    @Getter
    private String name;

    private COUNTRY(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static COUNTRY createFromCode(String code) {
        if (code == null) {
            return null;
        }
        for (COUNTRY country : values()) {
            if (code.equals(country.getCode())) {
                return country;
            }
        }
        return null;
    }
}
