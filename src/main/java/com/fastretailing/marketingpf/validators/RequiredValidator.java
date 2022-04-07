package com.fastretailing.marketingpf.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class RequiredValidator implements ConstraintValidator<Required, Object> {

    @Override
    public void initialize(Required type) {}

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext cxt) {
        if (value == null) {
            return false;
        }
        if (value instanceof String) {
            return !StringUtils.isAllBlank((String) value);
        }
        return true;
    }
}
