package com.delivery.service.book.validation;

import lombok.Getter;
import lombok.Setter;

public enum ValidationPattern {

	REGEX_SPECIAL_CHARACTER("^[a-zA-Z 0-9\\_=]*$", "No Special Characters Allowed"),
	REGEX_SPECIAL_CHARACTER_WITH_SPACE("^[a-zA-Z 0-9_ \\_=]*$", "No Special Characters Allowed. Whitespace is allowed"),
	REGEX_NUMERIC("\\d+", "Only numeric values accepted"),
	REGEX_ALPHANUMERIC("^[a-zA-Z0-9]+$", "Only alphanumeric values accepted"),
	REGEX_EMAIL("^(.+)@(.+)$", "Email format is invalid. Sample format x@x.com"),
	REGEX_PASSWORD("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!.]).{8,40})",
			"Password must have at least one uppercase, one lowercase, one number and one special charachter(@#$%!.)"),
	REGEX_ISBN(
			"^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$",
			"Invalid ISBN number. Sample format is ISBN 978-0-596-52068-7  ISBN-13: 978-0-596-52068-7 / 978 0 596 52068 7");

	@Getter
	@Setter
	private String regex;

	@Getter
	@Setter
	private String message;

	private ValidationPattern(String regex, String message) {
		this.regex = regex;
		this.message = message;
	}

}
