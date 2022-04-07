package com.fastretailing.marketingpf.validators;

import com.fastretailing.marketingpf.domain.enums.COUNTRY;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CountryValidator implements ConstraintValidator<CountryValid, String> {

    @Override
    public void initialize(CountryValid country) {}

    @Override
    public boolean isValid(String code, ConstraintValidatorContext cxt) {
        return code == null || COUNTRY.createFromCode(code) != null;
    }
}
