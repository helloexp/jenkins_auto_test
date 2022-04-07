package com.fastretailing.marketingpf.validators;

import com.fastretailing.marketingpf.domain.enums.BRAND;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BrandValidator implements ConstraintValidator<BrandValid, String> {

    @Override
    public void initialize(BrandValid brand) {}

    @Override
    public boolean isValid(String code, ConstraintValidatorContext cxt) {
        return code == null || BRAND.createFromCode(code) != null;
    }
}
