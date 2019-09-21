package com.check24.codingchallenge.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by Bassem
 */
@Entity
@Table(name = BookSimilarity.TABLE_NAME)
public class BookSimilarity {
    static final String TABLE_NAME = "BOOK_SIMILARITY";

    @EmbeddedId
    private BookSimilarityId bookSimilarityId;

    @ManyToOne(cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    @MapsId("firstBookId")
    @JsonBackReference
    private Book firstBook;

    @ManyToOne(cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    @MapsId("secondBookId")
    @JsonBackReference
    private Book secondBook;

    private double similarityScore;

    public BookSimilarity() {
    }

    public BookSimilarity(Book firstBook, Book secondBook, double similarityScore) {
        this.firstBook = firstBook;
        this.secondBook = secondBook;
        this.similarityScore = similarityScore;
        this.bookSimilarityId = new BookSimilarityId(firstBook.getId(), secondBook.getId());
    }


    public BookSimilarityId getBookSimilarityId() {
        return bookSimilarityId;
    }

    public void setBookSimilarityId(BookSimilarityId bookSimilarityId) {
        this.bookSimilarityId = bookSimilarityId;
    }

    public double getSimilarityScore() {
        return similarityScore;
    }

    public void setSimilarityScore(double similarityScore) {
        this.similarityScore = similarityScore;
    }

    public Book getFirstBook() {
        return firstBook;
    }

    public void setFirstBook(Book firstBook) {
        this.firstBook = firstBook;
    }

    public Book getSecondBook() {
        return secondBook;
    }

    public void setSecondBook(Book secondBook) {
        this.secondBook = secondBook;
    }
}
