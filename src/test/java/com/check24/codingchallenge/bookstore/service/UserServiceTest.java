package com.check24.codingchallenge.bookstore.service;

import com.check24.codingchallenge.bookstore.entity.User;
import com.check24.codingchallenge.bookstore.exception.AuthenticationException;
import com.check24.codingchallenge.bookstore.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

/**
 * This class is testing the functionality of the User Service class
 * Created by Bassem
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    /**
     * This validates if a registered logs in with his right credentials
     */
    @Test
    public void validCredentialsTest() {
        String email = "ab@gmail.com";
        String password = "1234";
        User testUser = new User();
        testUser.setEmail(email);
        testUser.setPassword(password);
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(testUser));
        User user = userService.validateUserCredentials(email, password);
        Mockito.verify(userRepository).findByEmail(email);
        Assert.assertEquals(user, testUser);

    }

    /**
     * This validates if a registered logs in with his wrong password credentials
     */
    @Test
    public void validMismatchingCredentialsTest() {
        String email = "ab@gmail.com";
        String password = "1234";
        User testUser = new User();
        testUser.setEmail(email);
        testUser.setPassword(password+"5");
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(testUser));
        User user = userService.validateUserCredentials(email, password);
        Mockito.verify(userRepository).findByEmail(email);
        Assert.assertEquals(user, null);

    }

    /**
     * This validates if a registered logs in with his wrong email
     */
    @Test(expected = AuthenticationException.class)
    public void validWrongEmailTest() {
        String email = "ab@gmail.com";
        String password = "1234";
        User testUser = new User();
        testUser.setEmail(email);
        testUser.setPassword(password);
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        User user = userService.validateUserCredentials(email, password);
        Mockito.verify(userRepository).findByEmail(email);


    }

}
