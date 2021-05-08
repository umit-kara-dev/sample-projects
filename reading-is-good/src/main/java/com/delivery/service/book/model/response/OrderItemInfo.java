package com.delivery.service.book.model.response;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class OrderItemInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private BookInfo bookInfo;
	
	@Getter
	@Setter
	private int orderCount;
	
	@Getter
	@Setter
	private BigDecimal amount;
	
	
}
