package com.fastretailing.marketingpf.validators;

import com.fastretailing.marketingpf.domain.enums.BRAND;
import com.fastretailing.marketingpf.domain.enums.BUSINESS_TYPE;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BusinessTypeValidator implements ConstraintValidator<BusinessTypeValid, String> {

    @Override
    public void initialize(BusinessTypeValid businessType) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext cxt) {
        return value == null || BUSINESS_TYPE.createFromValue(value) != null;
    }
}
