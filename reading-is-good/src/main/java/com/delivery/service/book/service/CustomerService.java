package com.delivery.service.book.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.service.book.exception.ResultCodeEnum;
import com.delivery.service.book.exception.RigException;
import com.delivery.service.book.model.entity.Customer;
import com.delivery.service.book.model.request.CreateCustomerRequest;
import com.delivery.service.book.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private EncryptService encryptService;
	
	/**
	 * create a customer for given info, encrpt and hash the password of customer.
	 * @param request
	 * @throws RigException
	 */
	public void createCustomer(CreateCustomerRequest request) throws RigException {
		log.info("createCustomer started for email = {}", request.getEmail());
		try {
			Customer customer = customerRepository.findByEmail(request.getEmail());
			if (customer != null) {
				throw new RigException(ResultCodeEnum.CUSTOMER_EXISTS);
			}
			customer = new Customer();
			customer.setAddress(request.getAddress());
			customer.setEmail(request.getEmail());
			customer.setName(request.getName());
			customer.setSurname(request.getSurname());
			customer.setInsertDate(LocalDateTime.now());
			customer.setInsertUser(request.getChannel());
			customer.setPassword(encryptService.encryptAndHash(request.getPassword()));
			customer = customerRepository.save(customer);
		} catch (RigException e) {
			log.error("Error at createCustomer. Error = {}", e.toString());
			throw e;
		} catch (Exception e) {
			log.error("Error at createCustomer. Error = {}", e);
			throw new RigException(ResultCodeEnum.SYSTEM_ERROR);
		} finally {
			log.info("createCustomer finished for email = {}", request.getEmail());
		}
	}

	public Customer findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}
}
