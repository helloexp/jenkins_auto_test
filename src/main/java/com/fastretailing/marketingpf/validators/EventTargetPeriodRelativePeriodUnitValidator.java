package com.fastretailing.marketingpf.validators;

import com.fastretailing.marketingpf.domain.enums.EVENT_TARGET_PERIOD_RELATIVE_PERIOD_UNIT;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EventTargetPeriodRelativePeriodUnitValidator implements ConstraintValidator<EventTargetPeriodRelativePeriodUnitValid, String> {

    @Override
    public void initialize(EventTargetPeriodRelativePeriodUnitValid eventTargetPeriodRelativeNumberValue) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext cxt) {
        return value == null || EVENT_TARGET_PERIOD_RELATIVE_PERIOD_UNIT.createFromValue(value) != null;
    }
}
