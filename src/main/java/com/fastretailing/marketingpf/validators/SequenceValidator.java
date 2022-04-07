package com.fastretailing.marketingpf.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SequenceValidator implements ConstraintValidator<SequenceValid, Long> {

    @Override
    public void initialize(SequenceValid type) {}

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext cxt) {
        return value == null || value > 0;
    }
}
