package com.fastretailing.marketingpf.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * Allow null<br>
 * But if not null, require valid value
 *
 */
@Documented
@Constraint(validatedBy = EventTargetPeriodTypeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EventTargetPeriodTypeValid {

    String message() default "errors.invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
