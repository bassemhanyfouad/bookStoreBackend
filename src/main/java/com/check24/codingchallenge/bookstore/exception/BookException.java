package com.check24.codingchallenge.bookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Bassem
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookException extends RuntimeException {

    public BookException(String message) {
        super(message);
    }

}

