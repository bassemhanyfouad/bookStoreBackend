package com.check24.codingchallenge.bookstore.controller;

import com.check24.codingchallenge.bookstore.AbstractIT;
import com.check24.codingchallenge.bookstore.payload.LoginRequest;
import com.check24.codingchallenge.bookstore.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

public class UserControllerIT extends AbstractIT {

    @Autowired
    UserRepository userRepository;

    @Test
    public void testSuccessfulLoginRequest() {
        LoginRequest loginRequest = new LoginRequest("ab@gmail.com", "1234");
        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest, new HttpHeaders());
        ResponseEntity responseEntity = restTemplate.exchange(createURLWithPort("/books-svc/task/login"),
                HttpMethod.POST, entity, String.class);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);


    }

    @Test
    public void testWrongCredentialsLoginRequest() {
        LoginRequest loginRequest = new LoginRequest("ab@gmail.com", "123");
        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest, new HttpHeaders());
        ResponseEntity responseEntity = restTemplate.exchange(createURLWithPort("/books-svc/task/login"),
                HttpMethod.POST, entity, String.class);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testWrongEmailLoginRequest() {
        LoginRequest loginRequest = new LoginRequest("ab@gmal.com", "1234");
        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest, new HttpHeaders());
        ResponseEntity responseEntity = restTemplate.exchange(createURLWithPort("/books-svc/task/login"),
                HttpMethod.POST, entity, String.class);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testInvalidEmailLoginRequest() {
        LoginRequest loginRequest = new LoginRequest("abgmail.com", "1234");
        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest, new HttpHeaders());
        ResponseEntity responseEntity = restTemplate.exchange(createURLWithPort("/books-svc/task/login"),
                HttpMethod.POST, entity, String.class);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}
