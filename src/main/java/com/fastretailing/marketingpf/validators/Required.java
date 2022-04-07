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
 * Null is not allowed<br>
 * Also, blank string is not allowed
 *
 */
@Documented
@Constraint(validatedBy = RequiredValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Required {

    String message() default "errors.required";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
