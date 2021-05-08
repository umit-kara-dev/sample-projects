package com.delivery.service.book.model.request;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.delivery.service.book.validation.SpecialCharacterNotAllowed;
import com.delivery.service.book.validation.ValidationPattern;

import lombok.Getter;
import lombok.Setter;

public class OperationRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Valid
	@Getter
	@Setter
	@NotNull
	@SpecialCharacterNotAllowed(regex = ValidationPattern.REGEX_NUMERIC)
	private String transactionId;
	
	@Valid
	@Getter
	@Setter
	@NotNull
	@SpecialCharacterNotAllowed(regex = ValidationPattern.REGEX_SPECIAL_CHARACTER)
	private String channel;
	

}
