package com.delivery.service.book.model.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

public class OrderDetailResponse extends OperationResponse {

	private static final long serialVersionUID = 1L;

	@Setter
	private List<OrderInfo> orderList;

	public List<OrderInfo> getOrderList() {
		if (orderList == null) {
			orderList = new ArrayList<OrderInfo>();
		}
		return orderList;
	}

}
