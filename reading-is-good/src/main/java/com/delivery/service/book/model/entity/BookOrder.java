package com.delivery.service.book.model.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
public class BookOrder extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@Column(unique = true)
	private String orderId;

	@Getter
	@Setter
	@OneToOne
	private Customer customer;

	@Getter
	@Setter
	private LocalDateTime orderDate;

	@Getter
	@Setter
	private int status;

	@Getter
	@Setter
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "order")
	private Set<OrderItem> orderItems = new HashSet<OrderItem>();

	public BookOrder(String insertUser, LocalDateTime insertDate) {
		super(insertUser, insertDate);
	}

}
