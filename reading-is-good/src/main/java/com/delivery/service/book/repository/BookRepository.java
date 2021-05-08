package com.delivery.service.book.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.delivery.service.book.model.entity.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

	public Book findByIsbn(String isbn);
}
