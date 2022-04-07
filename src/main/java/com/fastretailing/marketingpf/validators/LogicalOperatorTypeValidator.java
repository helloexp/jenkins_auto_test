package com.fastretailing.marketingpf.validators;

import com.fastretailing.marketingpf.domain.enums.LOGICAL_OPERATOR_TYPE;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LogicalOperatorTypeValidator implements ConstraintValidator<LogicalOperatorTypeValid, String> {

    @Override
    public void initialize(LogicalOperatorTypeValid logicalOperatorType) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext cxt) {
        return value == null || LOGICAL_OPERATOR_TYPE.createFromValue(value) != null;
    }
}
