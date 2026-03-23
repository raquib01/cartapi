package com.raquib.cartapi.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${spring.jwt.secret}")
    private String secret;

    public String generateToken(String username){
        final long expirationTime = 1000; // milliseconds in 24hrs
        return Jwts
                .builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .issuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();


    }

    public boolean validateToken(String token){
        try{
            Claims claims = Jwts
                            .parser()
                            .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                            .build()
                            .parseSignedClaims(token)
                            .getPayload();

            System.out.println(claims);
            return true;
        }catch (ExpiredJwtException e){
            System.out.println("token expired");
            return false;
        }catch (JwtException e){
            System.out.println("token invalid");
            return false;
        }

    }
}
