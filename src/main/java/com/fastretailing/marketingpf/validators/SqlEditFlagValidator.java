package com.fastretailing.marketingpf.validators;

import com.fastretailing.marketingpf.domain.enums.SQL_EDIT_FLAG;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SqlEditFlagValidator implements ConstraintValidator<SqlEditFlagValid, String> {

    @Override
    public void initialize(SqlEditFlagValid sqlEditFlag) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext cxt) {
        return value == null || SQL_EDIT_FLAG.createFromValue(value) != null;
    }
}
