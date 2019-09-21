package com.check24.codingchallenge.bookstore.payload;

import com.check24.codingchallenge.bookstore.security.JwtToken;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Bassem.
 */
public class JwtAuthenticationResponse {
    @ApiModelProperty(notes = "This is the access token")
    private JwtToken accessToken;

    private String tokenType = "Bearer";

    public JwtAuthenticationResponse(JwtToken accessToken) {
        this.accessToken = accessToken;
    }

    public JwtToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(JwtToken accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }


    @Override
    public String toString() {
        return "JwtAuthenticationResponse [accessToken=" + accessToken.toString() + ", tokenType=Bearer" + "]";
    }

}