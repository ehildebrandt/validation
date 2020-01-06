package com.example.validation;

import java.util.Objects;
import javax.validation.Validation;
import javax.validation.Validator;

public final class ValidatorHolder {

	private static class BillPughSingleton {  // inner class to provide instance of class
		private static final Validator VALIDATOR_INSTANCE = Validation.buildDefaultValidatorFactory().getValidator();
	}

	private ValidatorHolder() {
		if(Objects.nonNull(BillPughSingleton.VALIDATOR_INSTANCE)){
			throw new RuntimeException("This class can only be access through getValidator()");
		}
	}

	public static Validator getValidator() {
		return BillPughSingleton.VALIDATOR_INSTANCE;
	}

}
