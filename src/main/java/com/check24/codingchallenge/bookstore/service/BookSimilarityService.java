package com.check24.codingchallenge.bookstore.service;

import com.check24.codingchallenge.bookstore.entity.Book;
import com.check24.codingchallenge.bookstore.entity.BookSimilarity;

/**
 * Created by Bassem
 */
public interface BookSimilarityService {

    void fillSimilarityMatrixForBook(Book targetBook);

    double computeSimilarityScoreForBookPair(Book firstBook, Book secondBook);

    void createBookSimilarity(Book firstBook, Book secondBook, double similarityScore);

    void updateBookSimilarity(BookSimilarity bookSimilarity, double similarityScore);

    BookSimilarity findBookSimilarity(Book firstBook, Book secondBook);
}
