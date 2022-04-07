package com.fastretailing.marketingpf.validators;

import com.fastretailing.marketingpf.domain.enums.BATCH_JOB_STATUS;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BatchJobStatusValidator implements ConstraintValidator<BatchJobStatusValid, String> {

    @Override
    public void initialize(BatchJobStatusValid type) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext cxt) {
        return value == null || BATCH_JOB_STATUS.create(value) != null;
    }
}
