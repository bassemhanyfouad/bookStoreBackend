package com.check24.codingchallenge.bookstore.service;

import com.check24.codingchallenge.bookstore.entity.User;

/**
 * Created by Bassem
 */
public interface UserService {

     User validateUserCredentials(String email, String password);
}
