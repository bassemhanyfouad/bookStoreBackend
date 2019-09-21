package com.check24.codingchallenge.bookstore.service;

import com.check24.codingchallenge.bookstore.entity.Book;
import com.check24.codingchallenge.bookstore.entity.BookSimilarity;
import com.check24.codingchallenge.bookstore.entity.User;
import com.check24.codingchallenge.bookstore.exception.BookException;
import com.check24.codingchallenge.bookstore.exception.BookExceptionMessages;
import com.check24.codingchallenge.bookstore.exception.UserException;
import com.check24.codingchallenge.bookstore.exception.UserExceptionMessages;
import com.check24.codingchallenge.bookstore.repository.BookRepository;
import com.check24.codingchallenge.bookstore.repository.BookSimilarityRepository;
import com.check24.codingchallenge.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Bassem
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookSimilarityService bookSimilarityService;

    @Autowired
    BookSimilarityRepository bookSimilarityRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * This method retrieves a certain book by id and updates its views and trigger similarity calculations
     * @param bookId
     * @param userId
     * @return
     */
    @Override
    public Book viewABook(long bookId, long userId) {
        Book book = findById(bookId);
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(UserExceptionMessages.USER_NOT_FOUND));
        if (!book.getViewedBy().contains(user)) {
            book.addViewBy(user);
            //trigger asynchronously the calculation of similarities for this book
            bookSimilarityService.fillSimilarityMatrixForBook(book);
        }
        return book;

    }

    /**
     * This method finds book similarities for this book and extracts the
     * similar books
     *
     * @param bookId
     * @return
     */
    @Override
    public List<Book> findSimilarBooks(long bookId) {
        Book book = findById(bookId);
        List<BookSimilarity> bookSimilarities = bookSimilarityRepository.findBookSimilaritiesByBookSimilarityId_FirstBookIdOrBookSimilarityId_SecondBookIdOrderBySimilarityScoreDesc(book.getId(), book.getId());

        return bookSimilarities.stream().map((bs) ->
        {
            if (bs.getBookSimilarityId().getFirstBookId() == bookId) {

                return bs.getSecondBook();
            } else {
                return bs.getFirstBook();
            }


        }).collect(Collectors.toList());
    }

    public Book findById(long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookException(BookExceptionMessages.BOOK_NOT_FOUND));
        return book;
    }
}
