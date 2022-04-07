package com.fastretailing.marketingpf.validators;

import com.fastretailing.marketingpf.domain.enums.EVENT_TARGET_PERIOD_TYPE;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EventTargetPeriodTypeValidator implements ConstraintValidator<EventTargetPeriodTypeValid, String> {

    @Override
    public void initialize(EventTargetPeriodTypeValid eventTargetPeriodType) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext cxt) {
        return value == null || EVENT_TARGET_PERIOD_TYPE.createFromValue(value) != null;
    }
}
