
package com.delivery.service.book.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
public class OrderItem extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@OneToOne
	private BookOrder order;

	@Getter
	@Setter
	@OneToOne
	private Book book;
	
	@Getter
	@Setter
	private int orderCount;
	
	@Getter
	@Setter
	private BigDecimal amount;

	public OrderItem(String insertUser, LocalDateTime insertDate) {
		super(insertUser, insertDate);
	}

}
