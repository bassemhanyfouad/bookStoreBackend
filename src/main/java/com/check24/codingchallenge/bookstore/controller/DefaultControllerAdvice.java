package com.check24.codingchallenge.bookstore.controller;

/**
 * Created by Bassem
 */

import com.check24.codingchallenge.bookstore.exception.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class is responsible of intercepting exceptions throws from rest controllers and handle the response type
 * Created by Bassem
 */
@ControllerAdvice
public class DefaultControllerAdvice {

    //Code 401
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public void handleAuthenticationException() {
    }

    //Code 400
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleMethodArgumentNotValidException() {
    }


}
