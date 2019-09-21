package com.check24.codingchallenge.bookstore.security;

import com.check24.codingchallenge.bookstore.config.AppSettings;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * This class's aim is to validate JWT tokens and extract data from it.
 * <p>
 * Created by Bassem.
 */
@Service
public class JwtTokenInterpreter {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenInterpreter.class);

    @Autowired
    AppSettings appSettings;

    /**
     * This method extracts user Id from JWT token
     *
     * @param token
     * @return
     */
    public String getUserIdFromJWT(String token) {
        logger.info("--## getUserIdFromJWT: extract User id from Jwt Token ##--");
        Claims claims = Jwts.parser().setSigningKey(appSettings.getJwtSecret()).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }


    /**
     * This method validates JWT tokens by decrypting it against the jwtSecret
     *
     * @param authToken
     * @return
     */
    public boolean validateToken(String authToken) {
        logger.info("--## validateToken >> Validating JWT Token ##--");
        try {
            if (StringUtils.hasText(authToken)) {
                Jwts.parser().setSigningKey(appSettings.getJwtSecret()).parseClaimsJws(authToken);
                logger.info("--## validateToken << Valid JWT Token! ##--");
                return true;
            }
        } catch (SignatureException ex) {
            logger.error("--## validateToken << Invalid JWT signature ##--");
        } catch (MalformedJwtException ex) {
            logger.error("--## validateToken << Invalid JWT token ##--");
        } catch (ExpiredJwtException ex) {
            logger.error("--## validateToken << Expired JWT token ##--");
        } catch (UnsupportedJwtException ex) {
            logger.error("--## validateToken << Unsupported JWT token ##--");
        } catch (IllegalArgumentException ex) {
            logger.error("--## validateToken << JWT claims string is empty. ##--");
        }
        return false;
    }
}
