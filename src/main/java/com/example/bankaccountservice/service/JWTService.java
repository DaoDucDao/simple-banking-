package com.example.bankaccountservice.service;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class JWTService {
    private final long EXPIRATION = 1000 * 60 * 60 * 24;

    @Value("${jwt.secret}")
    private String secret;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String generateToken(UserDetails user) {
        return Jwts.builder().subject(user.getUsername()).issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION)).signWith(getSigningKey()).compact();
    }

    public String extractUserName(String token) {
        return Jwts.parser().verifyWith((SecretKey) getSigningKey()).build().parseSignedClaims(token).getPayload()
                .getSubject();
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser().verifyWith((SecretKey) getSigningKey()).build().parseSignedClaims(token).getPayload()
                .getExpiration().before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails user) {
        String username = extractUserName(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

}
