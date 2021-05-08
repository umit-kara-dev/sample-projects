package com.delivery.service.book.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public enum ResultCodeEnum {

	SUCCESS("0", "Success", true), //
	SYSTEM_ERROR("-1", "Fail", false), //
	VALIDATION_ERROR("-2", "ValidationFailed", false), //
	CUSTOMER_EXISTS("-3", "CustomerExists", false), //
	ORDER_NOT_FOUND("-4", "OrderNotFound", false), //
	CUSTOMER_NOT_FOUND("-5", "CustomerNotFound", false), //
	BOOK_NOT_FOUND("-6", "BookNotFound", false), //
	NOT_ENOUGH_STOCK("-7", "NotEnoughStock", false);

	@Getter
	@Setter
	private String resultCode;
	@Getter
	@Setter
	private String description;
	@Getter
	@Setter
	private boolean success;

}
