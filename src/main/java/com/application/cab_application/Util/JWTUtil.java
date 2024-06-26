package com.application.cab_application.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.UUID;

public class JWTUtil {
    private static final Algorithm ALGORITHM = Algorithm.HMAC256("fa42cd01ee4645073aa224065d53caea7bfb8b7644cc0c9cc719985ce0106cfe");
    private static final long EXPIRATION_TIME = 8 * 60 * 60 * 1000;
    private static final long REFRESH_TOKEN_EXPIRATION = 90L * 24 * 60 * 60 * 1000;

    public static String generateAccessToken(int accountID) {
        return JWT.create()
                .withClaim("accountID", accountID)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withJWTId(UUID.randomUUID()
                        .toString())
                .withNotBefore(new Date(System.currentTimeMillis() + 1000L))
                .sign(ALGORITHM);
    }

    public static String createRefreshToken() {
        return JWT.create()
                .withIssuer("auth0")
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .sign(ALGORITHM);
    }

    public static boolean verifyAuthToken(String token) {
        try {
            JWTVerifier jwtVerifier = JWT.require(ALGORITHM).build();
            DecodedJWT jwt = jwtVerifier.verify(token);
            return true;
        }catch (JWTVerificationException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static int getUserID(String token){
        JWTVerifier jwtVerifier = JWT.require(ALGORITHM).build();
        DecodedJWT jwt = jwtVerifier.verify(token);
        Claim claim = jwt.getClaim("accountID");
        return claim.asInt();
    }
}
