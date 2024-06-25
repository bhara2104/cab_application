package com.application.cab_application.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTUtil {
    private static final String SECRET_KEY = "d3b08de243cf33469e819efd92f4fcd1be8ae92def9fe3f61e1ae2330a1fabef";
    private static final long EXPIRATION_TIME = 8 * 60 * 60 * 1000;
    private static final long REFRESH_TOKEN_EXPIRATION = 90L * 24 * 60 * 60 * 1000;

    public static String generateAccessToken(int accountID) {
        return Jwts.builder()
                .setSubject(String.valueOf(accountID))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String generateRefreshToken(int accountID) {
        return Jwts.builder()
                .setSubject(String.valueOf(accountID))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
}
