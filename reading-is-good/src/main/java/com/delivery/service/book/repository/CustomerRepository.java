package com.delivery.service.book.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.delivery.service.book.model.entity.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

	public Customer findByEmail(String email);
}
