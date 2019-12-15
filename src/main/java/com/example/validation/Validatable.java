package com.example.validation;

import static com.example.validation.ValidatorHolder.getValidator;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.immutables.value.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.validation.annotation.Validated;

public interface Validatable {

	@Value.Check
	default void validate() {
		Class<?>[] groups = determineValidationGroups(getClass());
		Set<ConstraintViolation<Validatable>> result = getValidator().validate(this, groups);
		if (!result.isEmpty()) {
			throw new ConstraintViolationException(result);
		}
	}

	private static Class<?>[] determineValidationGroups(Class<?> clazz) {
		Validated validatedAnnotation =AnnotationUtils.findAnnotation(clazz, Validated.class);
		return (validatedAnnotation != null ? validatedAnnotation.value() : new Class<?>[0]);
	}

}
