package com.check24.codingchallenge.bookstore.service;

import com.check24.codingchallenge.bookstore.entity.Book;
import com.check24.codingchallenge.bookstore.entity.User;
import com.check24.codingchallenge.bookstore.exception.BookException;
import com.check24.codingchallenge.bookstore.exception.UserException;
import com.check24.codingchallenge.bookstore.repository.BookRepository;
import com.check24.codingchallenge.bookstore.repository.BookSimilarityRepository;
import com.check24.codingchallenge.bookstore.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

/**
 * This class is to test the functionality of the Book Service Impl
 * Created by Bassem
 */
@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookServiceImpl bookService;

    @Mock
    BookSimilarityRepository bookSimilarityRepository;

    @Mock
    BookSimilarityService bookSimilarityService;

    @Test
    public void testSuccessfulViewABook() {
        long userId = 1L;
        long bookId = 1L;
        User testUser = new User();
        testUser.setId(userId);

        Book testBook = new Book();
        testBook.setId(bookId);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(testBook));

        Book book = bookService.viewABook(bookId, userId);

        //1- verify that the viewBy and viewedbooks size became 1 and contain the right objects
        Assert.assertEquals(testBook.getViewedBy().size(), 1);
        Assert.assertEquals(testUser.getViewedBooks().size(), 1);
        Assert.assertEquals(testUser.getViewedBooks().contains(testBook), true);
        Assert.assertEquals(testBook.getViewedBy().contains(testUser), true);

        Mockito.verify(bookSimilarityService).fillSimilarityMatrixForBook(book);
        //2- verify that the returned object is the same as the mocked one.
        Assert.assertEquals(book, testBook);


    }

    @Test(expected = BookException.class)
    public void testFailedViewABookForWrongBookId() {
        long userId = 1L;
        long bookId = 1L;
        User testUser = new User();
        testUser.setId(userId);

        Book testBook = new Book();
        testBook.setId(bookId);

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Book book = bookService.viewABook(bookId, userId);

        Mockito.verify(userRepository, Mockito.never()).findById(userId);

        //1- verify that the viewBy and viewedbooks size is still 0
        Assert.assertEquals(testBook.getViewedBy().size(), 0);
        Assert.assertEquals(testUser.getViewedBooks().size(), 0);
        Assert.assertEquals(testUser.getViewedBooks().contains(testBook), false);
        Assert.assertEquals(testBook.getViewedBy().contains(testUser), false);

        //2- verify that the returned object is not the same as the mocked one.
        Assert.assertNotEquals(book, testBook);


    }

    @Test(expected = UserException.class)
    public void testFailedViewABookForWrongUserId() {
        long userId = 1L;
        long bookId = 1L;
        User testUser = new User();
        testUser.setId(userId);

        Book testBook = new Book();
        testBook.setId(bookId);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(testBook));

        Book book = bookService.viewABook(bookId, userId);


        //1- verify that the viewBy and viewedbooks size is still 0
        Assert.assertEquals(testBook.getViewedBy().size(), 0);
        Assert.assertEquals(testUser.getViewedBooks().size(), 0);
        Assert.assertEquals(testUser.getViewedBooks().contains(testBook), false);
        Assert.assertEquals(testBook.getViewedBy().contains(testUser), false);

        //2- verify that the returned object is not the same as the mocked one.
        Assert.assertNotEquals(book, testBook);


    }
}
