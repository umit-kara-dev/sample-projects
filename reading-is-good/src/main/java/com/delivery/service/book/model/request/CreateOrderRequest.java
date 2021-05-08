package com.delivery.service.book.model.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

public class CreateOrderRequest extends OperationRequest {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@NotEmpty
	@Valid
	private List<Order> itemList;

}
