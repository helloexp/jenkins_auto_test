package com.fastretailing.marketingpf.validators;

import com.fastretailing.marketingpf.domain.enums.EVENT_TARGET_PERIOD_TYPE;
import com.fastretailing.marketingpf.domain.enums.SEGMENT_STATUS;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SegmentStatusValidator implements ConstraintValidator<SegmentStatusValid, String> {

    @Override
    public void initialize(SegmentStatusValid segmentStatus) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext cxt) {
        return value == null || SEGMENT_STATUS.createFromValue(value) != null;
    }
}
