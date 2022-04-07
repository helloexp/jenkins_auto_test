package com.fastretailing.marketingpf.exceptions;

import org.springframework.http.HttpStatus;

public class MkdbApiUpstreamFailureException extends RuntimeException {
    private static final long serialVersionUID = 3205208020460727231L;

    public HttpStatus status;
    public String response;

    public MkdbApiUpstreamFailureException(String message, Throwable e, HttpStatus status, String response) {
        super(message, e);
        this.status = status;
        this.response = response;
    }
}
