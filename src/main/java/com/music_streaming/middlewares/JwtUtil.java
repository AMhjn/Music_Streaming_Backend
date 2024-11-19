package com.music_streaming.middlewares;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000; // 1 day

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY) // Use the SecretKey directly
                .compact();
    }

    public static String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // Use the SecretKey directly
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY) // Use the SecretKey directly
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
