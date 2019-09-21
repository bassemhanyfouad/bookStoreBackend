package com.check24.codingchallenge.bookstore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Bassem
 */
@Component
public class AppSettings {

    @Value("${app.jwtAccessTokenExpirationInMinutes}")
    private int jwtAccessTokenExpirationInM;

    @Value("${app.jwtSecret}")
    private String jwtSecret;


    public int getJwtAccessTokenExpirationInM() {
        return jwtAccessTokenExpirationInM;
    }

    public void setJwtAccessTokenExpirationInM(int jwtAccessTokenExpirationInM) {
        this.jwtAccessTokenExpirationInM = jwtAccessTokenExpirationInM;
    }

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }
}
