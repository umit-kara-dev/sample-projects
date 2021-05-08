package com.delivery.service.book.model.request;

import com.delivery.service.book.validation.SpecialCharacterNotAllowed;
import com.delivery.service.book.validation.ValidationPattern;

import lombok.Getter;
import lombok.Setter;

public class OrderDetailRequest extends OperationRequest {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@SpecialCharacterNotAllowed(regex = ValidationPattern.REGEX_NUMERIC)
	private String orderId;

}
