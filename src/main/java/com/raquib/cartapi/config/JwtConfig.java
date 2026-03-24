package com.raquib.cartapi.config;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
@ConfigurationProperties(prefix = "spring.jwt")
@Getter
@Setter
public class JwtConfig {
    private String secret;
    @Setter(AccessLevel.NONE)
    private Key key;
    private int accessTokenExpiration;
    private int refreshTokenExpiration;
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(this.secret.getBytes());
    }
}
