package com.example.validation;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ METHOD, FIELD, CONSTRUCTOR, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@interface PhoneNumberConstraint {

    String message() default "Invalid phone number format.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
