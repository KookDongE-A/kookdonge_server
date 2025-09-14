package com.kookdonge.kookdonge_server.auth.common;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWT {

    @Value("${auth.jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.expiration:86400000}")
    private long expiration;
    
    public String generateAccessToken(String externalUserId) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        
        return Jwts.builder()
                .subject(externalUserId)
                .issuer("kookdongeApiServer")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String externalUserId) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.builder()
                .subject(externalUserId)
                .issuer("kookdongeApiServer")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration * 7)) // Refresh token valid for 7 days
                .signWith(key)
                .compact();
    }
    
    public String extractUserId(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    
    public boolean isTokenValid(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
            
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
