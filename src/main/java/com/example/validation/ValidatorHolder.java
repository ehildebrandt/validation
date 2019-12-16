package com.example.validation;

import javax.validation.Validation;
import javax.validation.Validator;

public final class ValidatorHolder {

	private ValidatorHolder() {
	}

	private static class BillPughSingleton {  // inner class to provide instance of class
		private static final Validator VALIDATOR_INSTANCE = Validation.buildDefaultValidatorFactory().getValidator();
	}

	public static Validator getValidator() {
		return BillPughSingleton.VALIDATOR_INSTANCE;
	}

}
