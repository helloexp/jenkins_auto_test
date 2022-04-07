package com.fastretailing.marketingpf.domain.enums;

import lombok.Getter;

public enum BRAND {

    UQ("010", "UQ"),
    GU("090", "GU"),

    ;

    @Getter
    private String code;

    @Getter
    private String name;

    private BRAND(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static BRAND createFromCode(String code) {
        if (code == null) {
            return null;
        }
        for (BRAND brand : values()) {
            if (code.equals(brand.getCode())) {
                return brand;
            }
        }
        return null;
    }
}
