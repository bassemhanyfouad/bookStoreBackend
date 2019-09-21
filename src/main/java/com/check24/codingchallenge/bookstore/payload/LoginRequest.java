package com.check24.codingchallenge.bookstore.payload;

import com.check24.codingchallenge.bookstore.exception.AuthenticationException;
import com.check24.codingchallenge.bookstore.exception.AuthenticationExceptionMessages;
import com.check24.codingchallenge.bookstore.validator.ValidEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * This is the payload sent for login
 * Created by Bassem.
 */
public class LoginRequest {

    @NotBlank
    @NotNull
    @Email(message = AuthenticationExceptionMessages.INVALID_EMAIL)
    @ValidEmail(message = AuthenticationExceptionMessages.INVALID_EMAIL)
    private String email;

    @NotBlank
    @NotNull
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(@NotBlank @Email String email, @NotBlank String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + email + '\'' +
                ", password= #####'"  + '\'' +
                '}';
    }
}
