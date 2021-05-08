package com.delivery.service.book;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import com.delivery.service.book.exception.RigException;
import com.delivery.service.book.model.entity.Customer;
import com.delivery.service.book.repository.CustomerRepository;
import com.delivery.service.book.service.EncryptService;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableCaching
@EnableEncryptableProperties
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private EncryptService encryptService;
	@Autowired
	private CustomerRepository customerRepository;
	

	/**
	 * this init method is used for create first customer
	 * @throws RigException
	 */
	@PostConstruct
	public void initData() throws RigException {
		Customer customer = new Customer();
		customer.setName("customer1");
		customer.setSurname("surname1");
		customer.setEmail("1@x.com");
		customer.setAddress("address1");
		customer.setPassword(encryptService.encryptAndHash("pass1"));
		customerRepository.save(customer);

	}

}
