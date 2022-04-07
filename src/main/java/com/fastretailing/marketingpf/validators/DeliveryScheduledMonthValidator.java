package com.fastretailing.marketingpf.validators;

import com.fastretailing.marketingpf.domain.enums.COUNTRY;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DeliveryScheduledMonthValidator implements ConstraintValidator<DeliveryScheduledMonthValid, String> {

    @Override
    public void initialize(DeliveryScheduledMonthValid deliveryScheduledMonth) {}

    @Override
    public boolean isValid(String dateStr, ConstraintValidatorContext cxt) {
        if (dateStr == null) {
            return true;
        }

        // We must add default day, if not, we can not parse to LocalDate
        String dateStrWithDefaultDay = dateStr + "01";
        try {
            LocalDate.parse(dateStrWithDefaultDay, DateTimeFormatter.ofPattern("yyyyMMdd"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
