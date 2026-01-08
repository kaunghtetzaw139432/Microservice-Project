package com.gateway.filter;

import java.security.Key;
import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
      public static final String SECRET_KEY = "QDEyMyFBYmMjSGV5bm93JFRlc3RlcnRpbmcxMjM0NTY=";
    private Key getKey() {
        byte[] bytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);

    }
   public void validateToken(String token) {
       Jwts.parser()
       .verifyWith((SecretKey)getKey())
       .build()
       .parseSignedClaims(token);
   }
}
