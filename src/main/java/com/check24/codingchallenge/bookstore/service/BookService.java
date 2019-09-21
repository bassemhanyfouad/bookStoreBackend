package com.check24.codingchallenge.bookstore.service;

import com.check24.codingchallenge.bookstore.entity.Book;

import java.util.List;

/**
 * Created by Bassem
 */
public interface BookService {

     List<Book> getAllBooks();

     Book viewABook(long id, long userId);


     List<Book> findSimilarBooks(long bookId);
}
