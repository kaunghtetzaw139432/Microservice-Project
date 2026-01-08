package com.auth.utils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    public static final String SECRET_KEY = "QDEyMyFBYmMjSGV5bm93JFRlc3RlcnRpbmcxMjM0NTY=";

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", "kaung139432@gmail.com");
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .claims(claims)
                .signWith(getKey())
                .compact();
    }

    private Key getKey() {
        byte[] bytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);

    }

    private Claims getClaims(String token) {
        return Jwts.parser().verifyWith((SecretKey) getKey())
                .build().parseSignedClaims(token)
                .getPayload();
    }

    public Date getExpirationDate(String token) {
        return getClaims(token).getExpiration();
    }
}
