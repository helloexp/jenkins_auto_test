package com.fastretailing.marketingpf.exceptions;

public class AudienceCreateException extends RuntimeException {

    private static final long serialVersionUID = 3205208020460727231L;

    public AudienceCreateException(String message) {
        super(message);
    }

    public AudienceCreateException(String message, Throwable e) {
        super(message, e);
    }
}
