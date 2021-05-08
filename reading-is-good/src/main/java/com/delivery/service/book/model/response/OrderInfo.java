package com.delivery.service.book.model.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class OrderInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String orderId;

	@Getter
	@Setter
	private String orderDate;

	@Getter
	@Setter
	private int status;
	
	@Getter
	@Setter
	private BigDecimal totalAmount;

	@Getter
	@Setter
	private List<OrderItemInfo> orderItems = new ArrayList<OrderItemInfo>();

}
