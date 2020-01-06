package com.example.validation;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements

    ConstraintValidator<PhoneNumberConstraint, String> {

    private static final Pattern PATTERN = Pattern.compile("[0-9]+");

    @Override
    public void initialize(PhoneNumberConstraint contactNumber) {
    }

    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext cxt) {
        return contactField != null && PATTERN.matcher(contactField).matches()
            && (contactField.length() > 8) && (contactField.length() < 14);
    }

}