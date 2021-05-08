package com.delivery.service.book.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Customer extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private String surname;

	/**
	 * in a real system customer may have more addresses but this is a sample
	 * project so only one address is enough
	 *
	 */
	@Getter
	@Setter
	private String address;

	@Getter
	@Setter
	@Column(unique = true)
	private String email;

	@Getter
	@Setter
	@JsonIgnore
	private String password;

}
