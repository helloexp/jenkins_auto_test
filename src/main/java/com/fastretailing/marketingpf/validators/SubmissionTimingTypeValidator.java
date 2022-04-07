package com.fastretailing.marketingpf.validators;

import com.fastretailing.marketingpf.domain.enums.SUBMISSION_TIMING_TYPE;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SubmissionTimingTypeValidator implements ConstraintValidator<SubmissionTimingTypeValid, String> {

    @Override
    public void initialize(SubmissionTimingTypeValid submissionTimingType) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext cxt) {
        return value == null || SUBMISSION_TIMING_TYPE.createFromValue(value) != null;
    }
}
