package com.check24.codingchallenge.bookstore.controller;

import com.check24.codingchallenge.bookstore.entity.User;
import com.check24.codingchallenge.bookstore.exception.AuthenticationException;
import com.check24.codingchallenge.bookstore.security.JwtTokenFactory;
import com.check24.codingchallenge.bookstore.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Bassem
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {


    @Autowired
    MockMvc mvc;

    @MockBean
    JwtTokenFactory jwtTokenFactory;

    @MockBean
    UserServiceImpl userService;

    /**
     * This test validate the success response of the endpoint and that it did the right calls.
     *
     * @throws Exception
     */
    @Test
    public void testWithOkResponse() throws Exception {
        String email = "ab@gmail.com";
        String password = "1234";
        String body = "{\"email\": \"" + email + "\",\"password\":\"" + password + "\"}";
        System.out.println(body);
        when(userService.validateUserCredentials(email, password)).thenReturn(new User());
        mvc.perform(post("/task/login")
                .contentType("application/json")
                .content(body))
                .andExpect(status().isOk());

        verify(userService).validateUserCredentials(ArgumentMatchers.eq(email), ArgumentMatchers.eq(password));
    }

    /**
     * This test validate the success response of the endpoint and that it did the right calls.
     *
     * @throws Exception
     */
    @Test
    public void testWithUnauthorizedResponseForMismatchingCredentials() throws Exception {
        String email = "ab@gmail.com";
        String password = "1234";
        String body = "{\"email\": \"" + email + "\",\"password\":\"" + password + "\"}";
        System.out.println(body);
        when(userService.validateUserCredentials(email, password)).thenReturn(null);
        mvc.perform(post("/task/login")
                .contentType("application/json")
                .content(body))
                .andExpect(status().isUnauthorized());

        verify(userService).validateUserCredentials(ArgumentMatchers.eq(email), ArgumentMatchers.eq(password));
    }

    /**
     * This test validates the unauthorized response in case of wrong email
     *
     * @throws Exception
     */
    @Test
    public void testWithUnauthorizedResponseForWrongEmail() throws Exception {
        String email = "ab@gmail.com";
        String password = "1234";
        String body = "{\"email\": \"" + email + "\",\"password\":\"" + password + "\"}";
        System.out.println(body);
        when(userService.validateUserCredentials(email, password)).thenThrow(new AuthenticationException(""));
        mvc.perform(post("/task/login")
                .contentType("application/json")
                .content(body))
                .andExpect(status().isUnauthorized());

        verify(userService).validateUserCredentials(ArgumentMatchers.eq(email), ArgumentMatchers.eq(password));
    }

    /**
     * This test validates the unauthorized response in case of wrong email
     *
     * @throws Exception
     */
    @Test
    public void testInvalidEmail() throws Exception {
        String email = "bb@test.com";
        String password = "1234";
        String body = "{\"email\": \"" + email + "\",\"password\":\"" + password + "\"}";
        System.out.println(body);
        when(userService.validateUserCredentials(email, password)).thenReturn(new User());
        mvc.perform(post("/task/login")
                .contentType("application/json")
                .content(body))
                .andExpect(status().isBadRequest());
//                .andExpect(jsonPath("$[0].message", Matchers.is(AuthenticationExceptionMessages.INVALID_EMAIL)));


        verify(userService, never()).validateUserCredentials(ArgumentMatchers.eq(email), ArgumentMatchers.eq(password));
    }

    /**
     * This test validates the unauthorized response in case of wrong email
     *
     * @throws Exception
     */
    @Test
    public void testInvalidEmailWithWrongStructure() throws Exception {
        String email = "bb@testcom";
        String password = "1234";
        String body = "{\"email\": \"" + email + "\",\"password\":\"" + password + "\"}";
        System.out.println(body);
        when(userService.validateUserCredentials(email, password)).thenReturn(new User());
        mvc.perform(post("/task/login")
                .contentType("application/json")
                .content(body))
                .andExpect(status().isBadRequest());
//                .andExpect(jsonPath("$[0].message", Matchers.is(AuthenticationExceptionMessages.INVALID_EMAIL)));


        verify(userService, never()).validateUserCredentials(ArgumentMatchers.eq(email), ArgumentMatchers.eq(password));
    }

    /**
     * This test validates the unauthorized response in case of wrong email
     *
     * @throws Exception
     */
    @Test
    public void testEmptyEmail() throws Exception {
        String email = "";
        String password = "1234";
        String body = "{\"email\": \"" + email + "\",\"password\":\"" + password + "\"}";
        System.out.println(body);
        when(userService.validateUserCredentials(email, password)).thenReturn(new User());
        mvc.perform(post("/task/login")
                .contentType("application/json")
                .content(body))
                .andExpect(status().isBadRequest());
//                .andExpect(jsonPath("$[0].message", Matchers.is(AuthenticationExceptionMessages.INVALID_EMAIL)));


        verify(userService, never()).validateUserCredentials(ArgumentMatchers.eq(email), ArgumentMatchers.eq(password));
    }

    /**
     * This test validates the unauthorized response in case of empty password
     *
     * @throws Exception
     */
    @Test
    public void testEmptyPassword() throws Exception {
        String email = "aa@gmail.com";
        String password = "";
        String body = "{\"email\": \"" + email + "\",\"password\":\"" + password + "\"}";
        System.out.println(body);
        when(userService.validateUserCredentials(email, password)).thenReturn(new User());
        mvc.perform(post("/task/login")
                .contentType("application/json")
                .content(body))
                .andExpect(status().isBadRequest());
//                .andExpect(jsonPath("$[0].message", Matchers.is(AuthenticationExceptionMessages.INVALID_EMAIL)));


        verify(userService, never()).validateUserCredentials(ArgumentMatchers.eq(email), ArgumentMatchers.eq(password));
    }

    /**
     * Test for a null password as input
     *
     * @throws Exception
     */
    @Test
    public void testNullPassword() throws Exception {
        String email = "aa@gmail.com";
        String body = "{\"email\": \"" + email + "\",\"password\":\"" + "\"}";
        System.out.println(body);
        when(userService.validateUserCredentials(email, null)).thenReturn(new User());
        mvc.perform(post("/task/login")
                .contentType("application/json")
                .content(body))
                .andExpect(status().isBadRequest());
//                .andExpect(jsonPath("$[0].message", Matchers.is(AuthenticationExceptionMessages.INVALID_EMAIL)));


        verify(userService, never()).validateUserCredentials(ArgumentMatchers.eq(email), ArgumentMatchers.eq(null));
    }

    /**
     * Test for a null email as input
     *
     * @throws Exception
     */
    @Test
    public void testNullEmail() throws Exception {
        String email = null;
        String password = "1234";
        String body = "{\"email\": \"" + "\",\"password\":\"" + password + "\"}";
        System.out.println(body);
        when(userService.validateUserCredentials(email, password)).thenReturn(new User());
        mvc.perform(post("/task/login")
                .contentType("application/json")
                .content(body))
                .andExpect(status().isBadRequest());


        verify(userService, never()).validateUserCredentials(ArgumentMatchers.eq(email), ArgumentMatchers.eq(password));
    }


}
