package com.example.validation;

import javax.validation.Validation;
import javax.validation.Validator;

public class ValidatorHolder {

	private static final ThreadLocal<Validator> VALIDATOR_THREAD_LOCAL = new ThreadLocal<>();

	public static Validator getValidator() {
		Validator validator = VALIDATOR_THREAD_LOCAL.get();
		if (validator == null) {
			validator = Validation.buildDefaultValidatorFactory().getValidator();
			VALIDATOR_THREAD_LOCAL.set(validator);
		}
		return validator;
	}

}
