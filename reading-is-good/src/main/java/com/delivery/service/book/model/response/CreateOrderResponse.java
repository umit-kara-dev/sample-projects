package com.delivery.service.book.model.response;

import lombok.Getter;
import lombok.Setter;

public class CreateOrderResponse extends OperationResponse {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String orderId;

}
