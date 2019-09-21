package com.check24.codingchallenge.bookstore.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Bassem
 */
@Embeddable
public class BookSimilarityId implements Serializable {

    @Column(name = "FIRST_BOOK_ID")
    private long firstBookId;

    @Column(name = "SECOND_BOOK_ID")
    private long secondBookId;

    public BookSimilarityId() {
    }

    public BookSimilarityId(long firstBookId, long secondBookId) {
        this.firstBookId = firstBookId;
        this.secondBookId = secondBookId;
    }

    public long getFirstBookId() {
        return firstBookId;
    }

    public void setFirstBookId(long firstBookId) {
        this.firstBookId = firstBookId;
    }

    public long getSecondBookId() {
        return secondBookId;
    }

    public void setSecondBookId(long secondBookId) {
        this.secondBookId = secondBookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookSimilarityId that = (BookSimilarityId) o;
        return firstBookId == that.firstBookId &&
                secondBookId == that.secondBookId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstBookId, secondBookId);
    }
}
