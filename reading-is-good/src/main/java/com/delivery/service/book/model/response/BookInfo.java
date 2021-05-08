package com.delivery.service.book.model.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class BookInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Getter
	@Setter
	private String name;
	@Getter
	@Setter
	private String author;

}
