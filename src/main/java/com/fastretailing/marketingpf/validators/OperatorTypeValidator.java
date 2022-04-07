package com.fastretailing.marketingpf.validators;

import com.fastretailing.marketingpf.domain.enums.OPERATOR_TYPE;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OperatorTypeValidator implements ConstraintValidator<OperatorTypeValid, String> {

    @Override
    public void initialize(OperatorTypeValid operatorType) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext cxt) {
        return value == null || OPERATOR_TYPE.createFromValue(value) != null;
    }
}
