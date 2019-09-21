package com.check24.codingchallenge.bookstore.security;

import com.check24.codingchallenge.bookstore.config.AppSettings;
import com.check24.codingchallenge.bookstore.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by Bassem.
 */
@Component
public class JwtTokenFactory {

    @Autowired
    AppSettings appSettings;

    /**
     * This method generates access tokens using the user principal
     *
     * @param user
     * @return
     */
    public JwtToken generateAccessToken(User user) {


        JwtToken jwtToken = buildJwtAccessToken(user);

        return jwtToken;
    }

    /**
     * This method builds JWT access token using a list of claims, and user principal's username as subject,
     * expiry date, jwt secret
     *
     * @param user
     * @return
     */
    private JwtToken buildJwtAccessToken(User user) {
        LocalDateTime currentTime = LocalDateTime.now();
        Date expiryTime = Date.from(currentTime
                .plusMinutes(appSettings.getJwtAccessTokenExpirationInM())
                .atZone(ZoneId.systemDefault()).toInstant());

        String jwtToken = Jwts.builder()
                .setSubject(user.getId() + "")
                .setIssuedAt(new Date())
                .setExpiration(expiryTime)
                .signWith(SignatureAlgorithm.HS512, appSettings.getJwtSecret())
                .compact();
        return new JwtToken(jwtToken, expiryTime);
    }


}