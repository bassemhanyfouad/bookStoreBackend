package com.check24.codingchallenge.bookstore.controller;

import com.check24.codingchallenge.bookstore.entity.Book;
import com.check24.codingchallenge.bookstore.entity.User;
import com.check24.codingchallenge.bookstore.repository.BookRepository;
import com.check24.codingchallenge.bookstore.repository.UserRepository;
import com.check24.codingchallenge.bookstore.service.BookSimilarityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Bassem
 */
@RestController
@RequestMapping("/task/initialize")
public class InitializationController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookSimilarityService bookSimilarityService;

    @PostMapping
    public void initializeDatabase() {
        User user = new User();
        user.setId(1);
        user.setUserName("test1");
        user.setEmail("test1@check24.de");
        user.setPassword("1234");
        user.setMobileNumber("0123849824");
        user.setActive(true);
        userRepository.save(user);

        User user2 = new User();
        user2.setId(2);
        user2.setUserName("test2");
        user2.setEmail("test2@check24.de");
        user2.setPassword("1234");
        user2.setMobileNumber("012384924");
        user2.setActive(true);
        userRepository.save(user2);

        User user3 = new User();
        user3.setId(3);
        user3.setUserName("test3");
        user3.setEmail("test3@check24.de");
        user3.setPassword("1234");
        user3.setMobileNumber("01238824");
        user3.setActive(true);
        userRepository.save(user3);
        //create books
        Book book1 = new Book();
        book1.setName("Textbook A:  Unit testing basic principles");
        book1.setDetails("lorem ipsum, lorem ipsum, lorem ipsum,psum, lorem ipsum, lorem ipsum\n");
        book1.setPrice(75.234);
        book1.setImage("https://bilder.buecher.de/produkte/42/42530/42530510z.jpg\n");

        Book book2 = new Book();
        book2.setName("Textbook B:  Machine learning fundamentals");
        book2.setDetails("lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum\n");
        book2.setPrice(23.584);
        book2.setImage("https://bilder.buecher.de/produkte/48/48193/48193654z.jpg");

        Book book3 = new Book();
        book3.setName("Textbook C:  Domain driven design");
        book3.setDetails("lorem ipsum, lorem ipm ipsum, lorem ipsum\n");
        book3.setPrice(120);
        book3.setImage("https://bilder.buecher.de/produkte/49/49065/49065197z.jpg");

        //create book views
        book1.addViewBy(user);
        book1.addViewBy(user2);
        book2.addViewBy(user);
        book2.addViewBy(user2);
        book2.addViewBy(user3);
        book3.addViewBy(user3);


        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);


        bookSimilarityService.fillSimilarityMatrixForBook(book1);
        bookSimilarityService.fillSimilarityMatrixForBook(book2);
        bookSimilarityService.fillSimilarityMatrixForBook(book3);


    }
}