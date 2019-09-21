package com.check24.codingchallenge.bookstore.controller;

import com.check24.codingchallenge.bookstore.BookstoreApplication;
import com.check24.codingchallenge.bookstore.entity.User;
import com.check24.codingchallenge.bookstore.payload.LoginRequest;
import com.check24.codingchallenge.bookstore.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Bassem
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookstoreApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Autowired
    UserRepository userRepository;


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Before
    public void initialize() {
        User user = new User("012000292", "ab@gmail.com", "ab", "1234", true);
        userRepository.findByEmail("ab@gmail.com").orElseGet(() -> userRepository.save(user));
    }

    @Test
    public void testSuccessfulLoginRequest() {
        LoginRequest loginRequest = new LoginRequest("ab@gmail.com", "1234");
        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest, new HttpHeaders());
        ResponseEntity responseEntity = restTemplate.exchange(createURLWithPort("/task/login"),
                HttpMethod.POST, entity, String.class);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);


    }

    @Test
    public void testWrongCredentialsLoginRequest() {
        LoginRequest loginRequest = new LoginRequest("ab@gmail.com", "123");
        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest, new HttpHeaders());
        ResponseEntity responseEntity = restTemplate.exchange(createURLWithPort("/task/login"),
                HttpMethod.POST, entity, String.class);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testWrongEmailLoginRequest() {
        LoginRequest loginRequest = new LoginRequest("ab@gmal.com", "1234");
        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest, new HttpHeaders());
        ResponseEntity responseEntity = restTemplate.exchange(createURLWithPort("/task/login"),
                HttpMethod.POST, entity, String.class);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testInvalidEmailLoginRequest() {
        LoginRequest loginRequest = new LoginRequest("abgmail.com", "1234");
        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest, new HttpHeaders());
        ResponseEntity responseEntity = restTemplate.exchange(createURLWithPort("/task/login"),
                HttpMethod.POST, entity, String.class);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}
