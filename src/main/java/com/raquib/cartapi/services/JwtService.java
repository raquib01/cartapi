package com.raquib.cartapi.services;

import com.raquib.cartapi.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class JwtService {
    private final JwtConfig jwtConfig;

    public JwtService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String generateAccessToken(String username, String role){
        return Jwts
                .builder()
                .subject(username)
                .claim("role",role)
                .expiration(new Date(System.currentTimeMillis() + jwtConfig.getAccessTokenExpiration() * 1000))
                .issuedAt(new Date())
                .signWith(jwtConfig.getKey())
                .compact();


    }
    public String generateRefreshToken(String username, String role){
        return Jwts
                .builder()
                .subject(username)
                .claim("role",role)
                .expiration(new Date(System.currentTimeMillis() + jwtConfig.getRefreshTokenExpiration() * 1000))
                .issuedAt(new Date())
                .signWith(jwtConfig.getKey())
                .compact();


    }

    public Optional<Map<String,String>> validateToken(String token){
        try{
            Claims claims = Jwts
                            .parser()
                            .verifyWith((SecretKey) jwtConfig.getKey())
                            .build()
                            .parseSignedClaims(token)
                            .getPayload();
            return Optional.of(Map.of("username",claims.getSubject(),"role",claims.get("role",String.class)));
        }catch (ExpiredJwtException e){
            return Optional.empty();
        }catch (JwtException e){
            return Optional.empty();
        }

    }
}
