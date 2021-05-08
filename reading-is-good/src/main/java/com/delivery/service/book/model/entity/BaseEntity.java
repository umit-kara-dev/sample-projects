package com.delivery.service.book.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Getter
	@Setter
	private LocalDateTime insertDate;

	@Getter
	@Setter
	private String insertUser;

	@Getter
	@Setter
	private LocalDateTime updateDate;

	@Getter
	@Setter
	private String updateUser;

	public BaseEntity(String insertUser, LocalDateTime insertDate) {
		super();
		this.insertUser = insertUser;
		this.insertDate = insertDate;
	}
	
	

}