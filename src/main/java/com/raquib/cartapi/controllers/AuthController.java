package com.raquib.cartapi.controllers;

import com.raquib.cartapi.dtos.JwtResponse;
import com.raquib.cartapi.dtos.LoginRequest;
import com.raquib.cartapi.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response){
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        String role = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse(null);
        String accessToken = jwtService.generateAccessToken(loginRequest.getUsername(),role);
        String refreshToken = jwtService.generateRefreshToken(loginRequest.getUsername(),role);

        var cookie = new Cookie("refresh-token",refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/api/auth/refresh-token");
        cookie.setMaxAge(604800); // milliseconds in 7d
        // TODO: same site strict not set yet
        response.addCookie(cookie);
        return ResponseEntity.ok(new JwtResponse(accessToken));
    }
}
