package com.check24.codingchallenge.bookstore.service;

import com.check24.codingchallenge.bookstore.entity.Book;
import com.check24.codingchallenge.bookstore.entity.BookSimilarity;
import com.check24.codingchallenge.bookstore.entity.BookSimilarityId;
import com.check24.codingchallenge.bookstore.entity.User;
import com.check24.codingchallenge.bookstore.repository.BookSimilarityRepository;
import com.check24.codingchallenge.bookstore.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

/**
 * Created by Bassem
 */
@RunWith(MockitoJUnitRunner.class)
public class BookSimilarityServiceTest {
    @Mock
    BookServiceImpl bookService;

    @Mock
    UserRepository userRepository;

    @Mock
    BookSimilarityRepository bookSimilarityRepository;


    @InjectMocks
    BookSimilarityServiceImpl bookSimilarityService;


    @Test
    public void testFillSimilarityMatrixForBook() {
        BookSimilarityServiceImpl bookSimilarityServiceSpy = Mockito.spy(bookSimilarityService);
        Set<User> viewersOfFirstBook = new HashSet<>();
        Set<User> viewersOfSecondBook = new HashSet<>();
        Set<User> viewersOfThirdBook = new HashSet<>();
        Book testFirstBook = new Book();
        testFirstBook.setId(1);
        Book testSecondBook = new Book();
        testSecondBook.setId(2);
        Book testThirdBook = new Book();
        testThirdBook.setId(3);
        testFirstBook.getViewedBy().addAll(viewersOfFirstBook);
        testSecondBook.getViewedBy().addAll(viewersOfSecondBook);
        testThirdBook.getViewedBy().addAll(viewersOfThirdBook);
        List<Book> books = new ArrayList<>();
        books.add(testFirstBook);
        books.add(testSecondBook);
        books.add(testThirdBook);

        User user1 = new User();
        user1.setId(1);
        User user2 = new User();
        user2.setId(2);
        testFirstBook.getViewedBy().add(user1);
        testSecondBook.getViewedBy().add(user1);
        testSecondBook.getViewedBy().add(user2);
        testThirdBook.getViewedBy().add(user2);

        BookSimilarity testBookSimilarityBetween2And3 = new BookSimilarity(testSecondBook, testThirdBook, 0);
        Mockito.when(bookService.getAllBooks()).thenReturn(books);
        Mockito.doReturn(testBookSimilarityBetween2And3).when(bookSimilarityServiceSpy).findBookSimilarity(testThirdBook, testSecondBook);
        Mockito.doReturn(null).when(bookSimilarityServiceSpy).findBookSimilarity(testFirstBook, testSecondBook);
        bookSimilarityServiceSpy.fillSimilarityMatrixForBook(testSecondBook);
        Mockito.verify(bookService).getAllBooks();

        //verify that the computeSimilarityScoreForBookPair is never called for same book
        Mockito.verify(bookSimilarityServiceSpy, Mockito.never()).computeSimilarityScoreForBookPair(ArgumentMatchers.eq(testFirstBook), ArgumentMatchers.eq(testFirstBook));
        //verify that the computeSimilarityScoreForBookPair is called for book1 and book2 respectively
        Mockito.verify(bookSimilarityServiceSpy).computeSimilarityScoreForBookPair(ArgumentMatchers.eq(testFirstBook), ArgumentMatchers.eq(testSecondBook));
        Mockito.verify(bookSimilarityServiceSpy).computeSimilarityScoreForBookPair(ArgumentMatchers.eq(testThirdBook), ArgumentMatchers.eq(testSecondBook));

        Mockito.verify(bookSimilarityServiceSpy).createBookSimilarity(ArgumentMatchers.eq(testFirstBook), ArgumentMatchers.eq(testSecondBook), ArgumentMatchers.eq(0.71));

        Mockito.verify(bookSimilarityServiceSpy).updateBookSimilarity(ArgumentMatchers.eq(testBookSimilarityBetween2And3), ArgumentMatchers.eq(0.71));

    }

    /**
     * testComputeSimilarityScoreForTwoNonViewedBooks .. expected ->0
     */
    @Test
    public void testComputeSimilarityScoreForTwoNonViewedBooks() {
        Set<User> viewersOfFirstBook = new HashSet<>();
        Set<User> viewersOfSecondBook = new HashSet<>();
        Book testFirstBook = new Book();
        Book testSecondBook = new Book();
        testFirstBook.getViewedBy().addAll(viewersOfFirstBook);
        testSecondBook.getViewedBy().addAll(viewersOfSecondBook);

        //test that for 2 empty lists -> similarity is 0
        double similarity = bookSimilarityService.computeSimilarityScoreForBookPair(testFirstBook, testSecondBook);
        Assert.assertEquals(similarity, 0, 0);
    }

    /**
     * testComputeSimilarityScoreFor2Books where one is non Viewed .. expected ->0
     */
    @Test
    public void testComputeSimilarityScoreForTwoBooksWhereOnlyOneIsViewed() {
        Set<User> viewersOfFirstBook = new HashSet<>();
        Set<User> viewersOfSecondBook = new HashSet<>();
        Book testFirstBook = new Book();
        Book testSecondBook = new Book();
        testFirstBook.getViewedBy().addAll(viewersOfFirstBook);
        testSecondBook.getViewedBy().addAll(viewersOfSecondBook);
        User user1 = new User();
        user1.setId(1);
        User user2 = new User();
        user2.setId(2);
        testFirstBook.getViewedBy().add(user1);

        double similarity = bookSimilarityService.computeSimilarityScoreForBookPair(testFirstBook, testSecondBook);
        Assert.assertEquals(similarity, 0, 0);
    }

    /**
     * testComputeSimilarityScoreFor2Books having same viewers .. expected ->1
     */
    @Test
    public void testComputeSimilarityScoreForTwoBooksHavingSameViewers() {
        Set<User> viewersOfFirstBook = new HashSet<>();
        Set<User> viewersOfSecondBook = new HashSet<>();
        Book testFirstBook = new Book();
        Book testSecondBook = new Book();
        testFirstBook.getViewedBy().addAll(viewersOfFirstBook);
        testSecondBook.getViewedBy().addAll(viewersOfSecondBook);
        User user1 = new User();
        user1.setId(1);
        User user2 = new User();
        user2.setId(2);
        testFirstBook.getViewedBy().add(user1);
        testSecondBook.getViewedBy().add(user1);

        double similarity = bookSimilarityService.computeSimilarityScoreForBookPair(testFirstBook, testSecondBook);
        Assert.assertEquals(similarity, 1, 0);
    }

    /**
     * testComputeSimilarityScoreFor2Books having completely different viewers .. expected ->1
     */
    @Test
    public void testComputeSimilarityScoreForTwoBooksHavingDifferentViewers() {
        Set<User> viewersOfFirstBook = new HashSet<>();
        Set<User> viewersOfSecondBook = new HashSet<>();
        Book testFirstBook = new Book();
        Book testSecondBook = new Book();
        testFirstBook.getViewedBy().addAll(viewersOfFirstBook);
        testSecondBook.getViewedBy().addAll(viewersOfSecondBook);
        User user1 = new User();
        user1.setId(1);
        User user2 = new User();
        user2.setId(2);
        testFirstBook.getViewedBy().add(user1);
        testSecondBook.getViewedBy().add(user2);

        double similarity = bookSimilarityService.computeSimilarityScoreForBookPair(testFirstBook, testSecondBook);
        Assert.assertEquals(similarity, 0, 0);
    }

    /**
     * testComputeSimilarityScoreFor2Books .. expected ->0.71
     * test 2 book with half similairity.. common viewers is 1,
     * number of viewers for the first book is 1 and for the second is 2
     */
    @Test
    public void testComputeSimilarityScoreForTwoBooks() {
        //test 2 books having same viewers the similarity is 1
        Set<User> viewersOfFirstBook = new HashSet<>();
        Set<User> viewersOfSecondBook = new HashSet<>();
        Book testFirstBook = new Book();
        Book testSecondBook = new Book();
        testFirstBook.getViewedBy().addAll(viewersOfFirstBook);
        testSecondBook.getViewedBy().addAll(viewersOfSecondBook);
        User user1 = new User();
        user1.setId(1);
        User user2 = new User();
        user2.setId(2);
        testFirstBook.getViewedBy().add(user1);
        testSecondBook.getViewedBy().add(user1);
        testSecondBook.getViewedBy().add(user2);

        double similarity = bookSimilarityService.computeSimilarityScoreForBookPair(testFirstBook, testSecondBook);
        Assert.assertEquals(similarity, 0.71, 0);
    }

    /**
     * This method tests the find book similarity that it finds by the 2 book id regardless the order
     */
    @Test
    public void testFindBookSimilarity() {
        Book testFirstBook = new Book();
        Book testSecondBook = new Book();
        testFirstBook.setId(1);
        testSecondBook.setId(2);
        ArgumentCaptor<BookSimilarityId> firstArgument = ArgumentCaptor.forClass(BookSimilarityId.class);
        ArgumentCaptor<BookSimilarityId> secondArgument = ArgumentCaptor.forClass(BookSimilarityId.class);
        bookSimilarityService.findBookSimilarity(testFirstBook, testSecondBook);
        Mockito.verify(bookSimilarityRepository).findByBookSimilarityIdOrBookSimilarityId(firstArgument.capture(), secondArgument.capture());
        Assert.assertEquals(firstArgument.getValue().getFirstBookId(), testFirstBook.getId());
        Assert.assertEquals(firstArgument.getValue().getSecondBookId(), testSecondBook.getId());
        Assert.assertEquals(secondArgument.getValue().getFirstBookId(), testSecondBook.getId());
        Assert.assertEquals(secondArgument.getValue().getSecondBookId(), testFirstBook.getId());


    }

    /**
     * Test the update book similarity functionality
     */
    @Test
    public void testUpdateBookSimilarity() {
        Book testFirstBook = new Book();
        Book testSecondBook = new Book();
        testFirstBook.setId(1);
        testSecondBook.setId(2);
        BookSimilarity bookSimilarity = new BookSimilarity(testFirstBook, testSecondBook, 0);
        bookSimilarityService.updateBookSimilarity(bookSimilarity, 2);
        Mockito.verify(bookSimilarityRepository).save(bookSimilarity);
        Assert.assertEquals(bookSimilarity.getSimilarityScore(), 2, 0);
    }

    /**
     * This method tests the create book similarity functionality
     */
    @Test
    public void testCreateBookSimilarity() {
        Book testFirstBook = new Book();
        Book testSecondBook = new Book();
        testFirstBook.setId(1);
        testSecondBook.setId(2);

        ArgumentCaptor<BookSimilarity> argument = ArgumentCaptor.forClass(BookSimilarity.class);
        bookSimilarityService.createBookSimilarity(testFirstBook, testSecondBook, 1);
        Mockito.verify(bookSimilarityRepository).save(argument.capture());

        Assert.assertEquals(argument.getValue().getSimilarityScore(), 1, 0);
        Assert.assertEquals(argument.getValue().getFirstBook(), testFirstBook);
        Assert.assertEquals(argument.getValue().getSecondBook(), testSecondBook);
        Assert.assertEquals(argument.getValue().getBookSimilarityId().getFirstBookId(), testFirstBook.getId());
        Assert.assertEquals(argument.getValue().getBookSimilarityId().getSecondBookId(), testSecondBook.getId());


    }

}
