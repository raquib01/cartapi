package com.raquib.cartapi.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {
    @Value("${spring.jwt.secret}")
    private String secret;

    public String generateToken(String username){
        final long expirationTime = 86400000; // milliseconds in 24hrs
        return Jwts
                .builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .issuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();


    }

    public Optional<String> validateToken(String token){
        try{
            Claims claims = Jwts
                            .parser()
                            .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                            .build()
                            .parseSignedClaims(token)
                            .getPayload();
            return Optional.of(claims.getSubject());
        }catch (ExpiredJwtException e){
            return Optional.empty();
        }catch (JwtException e){
            return Optional.empty();
        }

    }
}
