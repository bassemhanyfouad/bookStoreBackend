package com.check24.codingchallenge.bookstore.controller;

import com.check24.codingchallenge.bookstore.entity.User;
import com.check24.codingchallenge.bookstore.exception.AuthenticationException;
import com.check24.codingchallenge.bookstore.exception.AuthenticationExceptionMessages;
import com.check24.codingchallenge.bookstore.payload.JwtAuthenticationResponse;
import com.check24.codingchallenge.bookstore.payload.LoginRequest;
import com.check24.codingchallenge.bookstore.security.JwtToken;
import com.check24.codingchallenge.bookstore.security.JwtTokenFactory;
import com.check24.codingchallenge.bookstore.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Bassem
 */
@RestController
@RequestMapping("/task/login")
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = {"Login API"}, value = "Login API", description = "This API is used to authenticate users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenFactory jwtTokenFactory;

    /**
     * This endpoint serves as a login entry point for outsiders to request jwt tokens
     *
     * @param loginRequest
     * @return
     */
    @ApiOperation(value = "This endpoint is for authenticating users and generating JWT tokens")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 401, message = "Cannot Authenticate the user, User is Inactive!")
    })
    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        User user = userService.validateUserCredentials(loginRequest.getEmail(), loginRequest.getPassword());
        if (user == null) {
            throw new AuthenticationException(AuthenticationExceptionMessages.EMAIL_PASSWORD_MISMATCH);
        }
        JwtToken accessToken = jwtTokenFactory.generateAccessToken(user);
        return ResponseEntity.ok(new JwtAuthenticationResponse(accessToken));
    }


}
