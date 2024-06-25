package com.application.cab_application.Util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTUtil {
    private static final String SECRET_KEY = "95a1d6e351c641417065fa25e066c927035e933ed254cd8948c4fd1581e65e2c";
    private static final long EXPIRATION_TIME = 8 * 60 * 60 * 1000;
    private static final long REFRESH_TOKEN_EXPIRATION = 90L * 24 * 60 * 60 * 1000;

    public static String generateAccessToken(int accountID) {
        return Jwts.builder()
                .subject(String.valueOf(accountID))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
    }

    public static String generateRefreshToken(int accountID) {
        return Jwts.builder()
                .subject("{accountID:+"+accountID+"}")
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
    }

//    public static boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    public static Claims getClaims(String token) {
//        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
//    }
}
