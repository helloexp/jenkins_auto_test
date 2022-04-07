package com.fastretailing.marketingpf.validators;

import com.fastretailing.marketingpf.domain.enums.BATCH_JOB_TYPE;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BatchJobTypeValidator implements ConstraintValidator<BatchJobTypeValid, String> {


    @Override
    public void initialize(BatchJobTypeValid type) {}

    @Override
    public boolean isValid(String code, ConstraintValidatorContext cxt) {
        return code == null || BATCH_JOB_TYPE.createFromValue(code) != null;
    }
}
