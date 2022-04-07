package com.fastretailing.marketingpf.controllers.error;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ERROR_CODE {
    E00400("E00400", "Bad request"),
    E00401("E00401", "Unauthorized"),
    E00403("E00403", "Permission denied"),
    E01403("E01403", "Insufficient sql segment role"),
    E00404("E00404", "The request resource is not found"),
    E00500("E00500", "Something wrong occur"),
    E01500("E01500", "Mkdb api response error"),
    E02500("E02500", "Mkdb api request error"),
    E00417("E00417", "File is too big"),
    E01401("E01401", "Not Login"),

    ;

    @JsonValue
    private String code;

    private String desc;

    private ERROR_CODE(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
