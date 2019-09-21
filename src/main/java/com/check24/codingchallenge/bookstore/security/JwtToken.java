package com.check24.codingchallenge.bookstore.security;

import com.check24.codingchallenge.bookstore.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * This a basic class for a normal JWT token
 * Created by Bassem.
 */
public class JwtToken {

    @ApiModelProperty(notes = "This is the JWT token")
    private String jwtToken;

    @ApiModelProperty(notes = "This is the expiry time of the JWT token")
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date expiryDate;

    public JwtToken(String jwtToken, Date expiryDate) {
        this.jwtToken = jwtToken;
        this.expiryDate = expiryDate;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "JwtToken [jwtToken=#####" + ", expiryDate=" + expiryDate + "]";
    }
}