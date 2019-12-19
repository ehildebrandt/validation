package com.example.validation;

import static com.example.validation.ValidatorHolder.getValidator;

import java.util.Optional;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.immutables.value.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.validation.annotation.Validated;

public interface Validatable {

	@Value.Check
	default void validate() {
		Class<?>[] groups = Optional.ofNullable(AnnotationUtils.findAnnotation(getClass(), Validated.class))
			.map(Validated::value)
			.orElse(new Class<?>[0]);
		Set<ConstraintViolation<Validatable>> result = getValidator().validate(this, groups);
		if (!result.isEmpty()) {
			throw new ConstraintViolationException(result);
		}
	}
}
