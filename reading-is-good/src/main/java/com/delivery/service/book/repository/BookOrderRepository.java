package com.delivery.service.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.delivery.service.book.model.entity.BookOrder;

@Repository
public interface BookOrderRepository extends CrudRepository<BookOrder, Long> {
	
	public BookOrder findByOrderId(String orderId);
	
	@Query(value = "select bo from BookOrder bo where bo.customer.email=:email")
	public List<BookOrder> findByCustomer(String email);

}
