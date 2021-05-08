package com.delivery.service.book.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SpecialCharacterValidator implements ConstraintValidator<SpecialCharacterNotAllowed, String> {

	private int maxLength;
	private ValidationPattern validationPattern;

	@Override
	public void initialize(SpecialCharacterNotAllowed constraintAnnotation) {
		maxLength = constraintAnnotation.length();
		validationPattern = constraintAnnotation.regex();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(validationPattern.getMessage()).addConstraintViolation();
		if (value == null || (value != null && !value.isBlank() && value.matches(validationPattern.getRegex())
				&& value.length() <= maxLength)) {
			return true;
		} else {
			return false;
		}
	}

}
