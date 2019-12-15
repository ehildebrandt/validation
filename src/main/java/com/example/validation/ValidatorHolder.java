package com.example.validation;

import javax.validation.Validation;
import javax.validation.Validator;

public class ValidatorHolder {

	private static class BillPughSingleton {  // Inner class to provide instance of class
		private static final ThreadLocal<Validator> VALIDATOR_THREAD_LOCAL = new ThreadLocal<>();
	}

	public static Validator getValidator() {
		Validator validator = BillPughSingleton.VALIDATOR_THREAD_LOCAL.get();
		if (validator == null) {
			validator = Validation.buildDefaultValidatorFactory().getValidator();
			BillPughSingleton.VALIDATOR_THREAD_LOCAL.set(validator);
		}
		return validator;
	}

}
