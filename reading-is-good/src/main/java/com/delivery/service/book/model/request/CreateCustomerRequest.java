package com.delivery.service.book.model.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.delivery.service.book.validation.SpecialCharacterNotAllowed;
import com.delivery.service.book.validation.ValidationPattern;

import lombok.Getter;
import lombok.Setter;

public class CreateCustomerRequest extends OperationRequest {

	private static final long serialVersionUID = 1L;

	@Valid
	@Getter
	@Setter
	@SpecialCharacterNotAllowed(regex = ValidationPattern.REGEX_SPECIAL_CHARACTER_WITH_SPACE)
	@NotNull
	private String name;

	@Getter
	@Setter
	@SpecialCharacterNotAllowed(regex = ValidationPattern.REGEX_SPECIAL_CHARACTER_WITH_SPACE)
	@NotNull
	private String surname;

	@Getter
	@Setter
	@NotNull
	private String address;
	
	@Valid
	@Getter
	@Setter
	@SpecialCharacterNotAllowed(regex = ValidationPattern.REGEX_EMAIL)
	@NotNull
	private String email;
	
	@Getter
	@Setter
	@SpecialCharacterNotAllowed(regex = ValidationPattern.REGEX_PASSWORD)
	@NotNull
	private String password;


}
