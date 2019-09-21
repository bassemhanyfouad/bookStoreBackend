package com.check24.codingchallenge.bookstore.service;

import com.check24.codingchallenge.bookstore.entity.User;
import com.check24.codingchallenge.bookstore.exception.AuthenticationException;
import com.check24.codingchallenge.bookstore.exception.AuthenticationExceptionMessages;
import com.check24.codingchallenge.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Bassem
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * This method validates that the user credentials are valid
     * @param email
     * @param password
     * @return
     */
    @Override
    public User validateUserCredentials(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AuthenticationException(AuthenticationExceptionMessages.EMAIL_NOT_FOUND));
        if (user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}