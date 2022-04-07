package com.fastretailing.marketingpf.exceptions;

import com.fastretailing.marketingpf.controllers.error.ErrorResponse.ValidationError;
import java.util.List;
import lombok.Getter;

public class ValidationFailException extends RuntimeException {

    private static final long serialVersionUID = -3129977537099470278L;

    @Getter
    private List<ValidationError> validationErrors;

    public ValidationFailException(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
