package com.check24.codingchallenge.bookstore.repository;

import com.check24.codingchallenge.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Bassem
 */
public interface BookRepository extends JpaRepository<Book, Long> {
}
