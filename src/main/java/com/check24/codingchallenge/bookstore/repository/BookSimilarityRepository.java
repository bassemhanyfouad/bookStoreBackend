package com.check24.codingchallenge.bookstore.repository;

import com.check24.codingchallenge.bookstore.entity.Book;
import com.check24.codingchallenge.bookstore.entity.BookSimilarity;
import com.check24.codingchallenge.bookstore.entity.BookSimilarityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Bassem
 */
@Repository
public interface BookSimilarityRepository extends JpaRepository<BookSimilarity, BookSimilarityId> {

    public Optional<BookSimilarity> findByBookSimilarityIdOrBookSimilarityId(BookSimilarityId firstCombination, BookSimilarityId secondCombination);

    public List<BookSimilarity> findBookSimilaritiesByBookSimilarityId_FirstBookIdOrBookSimilarityId_SecondBookIdOrderBySimilarityScoreDesc(long firstBookId, long secondBookId);
}
