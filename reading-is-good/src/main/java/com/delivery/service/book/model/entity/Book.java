package com.delivery.service.book.model.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Book extends BaseEntity {

	private static final long serialVersionUID = 1L;
	@Getter
	@Setter
	@Column(unique = true)
	private String isbn;
	@Getter
	@Setter
	private String name;
	@Getter
	@Setter
	private String author;
	@Getter
	@Setter
	private Integer pageNumber;
	@Getter
	@Setter
	private BigDecimal price;
	@Getter
	@Setter
	private int stockCount = 0;

	public void decreaseStockCount(int count) {
		stockCount = stockCount - count;
	}

	public void increaseStockCount(int count) {
		stockCount = stockCount + count;
	}
}
