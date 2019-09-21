package com.check24.codingchallenge.bookstore.service;

import com.check24.codingchallenge.bookstore.entity.Book;
import com.check24.codingchallenge.bookstore.entity.BookSimilarity;
import com.check24.codingchallenge.bookstore.entity.BookSimilarityId;
import com.check24.codingchallenge.bookstore.entity.User;
import com.check24.codingchallenge.bookstore.repository.BookSimilarityRepository;
import com.check24.codingchallenge.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Bassem
 */
@Service
public class BookSimilarityServiceImpl implements BookSimilarityService {

    @Autowired
    BookService bookService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookSimilarityRepository bookSimilarityRepository;

    /**
     * Computes the similarities for a given book with all the database books in O(n)
     */
    @Async
    @Override
    public void fillSimilarityMatrixForBook(Book targetBook) {
        List<Book> allBooks = bookService.getAllBooks();
        //1- find all books
        allBooks.parallelStream().forEach((firstBook) -> {
            if (firstBook.getId() != targetBook.getId()) {
                //2- compute the similarity for the target book with each book
                double computedSimilarity = computeSimilarityScoreForBookPair(firstBook, targetBook);
                // 3- find similarity row
                BookSimilarity bookSimilarity = findBookSimilarity(firstBook, targetBook);
                if (bookSimilarity == null) {
                    //create a new similarity row if it does not exist
                    createBookSimilarity(firstBook, targetBook, computedSimilarity);
                } else {
                    //update the already existing similarity row
                    updateBookSimilarity(bookSimilarity, computedSimilarity);
                }

            }
        });

    }

    /**
     * Since for each book it is either viewed or unviewed by a certain 0 , the matrix is Zeros and Ones.
     * The implementation is focusing on the ones since the zero will always yield a zero.
     * The product is the number of common viewers between the 2 books
     * The square root of the first book is the root of (the number of first book viewers *1^2)
     * The square root of the second book is the root of (the number of second book viewers *1^2)
     *
     * @param firstBook
     * @param secondBook
     */
    @Override
    public double computeSimilarityScoreForBookPair(Book firstBook, Book secondBook) {
        Set<User> viewersOfFirstBook = firstBook.getViewedBy();
        Set<User> viewersOfSecondBook = secondBook.getViewedBy();

        //is both books have no viewed.. the similarity is 0
        boolean bothBookAreEmpty = viewersOfFirstBook.isEmpty() && viewersOfSecondBook.isEmpty();
        //if any of the 2 books has no viewers .. the similarity is 0
        boolean anyOfThe2BookIsEmpty = viewersOfFirstBook.isEmpty() || viewersOfSecondBook.isEmpty();
        if (bothBookAreEmpty || anyOfThe2BookIsEmpty) {
            return 0;
        }
        //1- aggregate the common viewers between the 2 books
        Set<User> commonUsers = new HashSet<>();
        commonUsers.addAll(viewersOfFirstBook);
        //complexity O(n)
        commonUsers.retainAll(viewersOfSecondBook);

        //2- compute each part of the cosine equation
        BigDecimal product = BigDecimal.valueOf(commonUsers.size());
        BigDecimal firstRoot = BigDecimal.valueOf(Math.sqrt(viewersOfFirstBook.size()));
        BigDecimal secondRoot = BigDecimal.valueOf(Math.sqrt(viewersOfSecondBook.size()));

        //3- execute the cosine similarity rule
        return product.divide(firstRoot.multiply(secondRoot), 2, RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public void createBookSimilarity(Book firstBook, Book secondBook, double similarityScore) {
        BookSimilarity bookSimilarity = new BookSimilarity(firstBook, secondBook, similarityScore);
        bookSimilarityRepository.save(bookSimilarity);
    }

    @Override
    public void updateBookSimilarity(BookSimilarity bookSimilarity, double similarityScore) {
        bookSimilarity.setSimilarityScore(similarityScore);
        bookSimilarityRepository.save(bookSimilarity);
    }

    @Override
    public BookSimilarity findBookSimilarity(Book firstBook, Book secondBook) {
        BookSimilarityId firstCombination = new BookSimilarityId(firstBook.getId(), secondBook.getId());
        BookSimilarityId secondCombination = new BookSimilarityId(secondBook.getId(), firstBook.getId());
        BookSimilarity bookSimilarity = bookSimilarityRepository.findByBookSimilarityIdOrBookSimilarityId(firstCombination, secondCombination).orElse(null);
        return bookSimilarity;
    }
}
