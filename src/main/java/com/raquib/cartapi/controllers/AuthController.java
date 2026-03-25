package com.raquib.cartapi.controllers;

import com.raquib.cartapi.config.JwtConfig;
import com.raquib.cartapi.dtos.JwtResponse;
import com.raquib.cartapi.dtos.LoginRequest;
import com.raquib.cartapi.services.JwtService;
import com.raquib.cartapi.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    private final JwtConfig jwtConfig;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, JwtConfig jwtConfig, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.jwtConfig = jwtConfig;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response){
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        String role = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse(null);
        String accessToken = jwtService.    generateAccessToken(loginRequest.getUsername(),role);
        String refreshToken = jwtService.generateRefreshToken(loginRequest.getUsername(),role);

        var cookie = new Cookie("refresh-token",refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/api/auth/refresh-token");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
        response.addCookie(cookie);
        return ResponseEntity.ok(new JwtResponse(accessToken));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtResponse> refreshToken(@CookieValue(value = "refresh-token",required = false) String refreshToken){
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var data = jwtService.validateToken(refreshToken).orElse(null);
        if(data!=null){
            var user = userService.getUser(data.get("username"));
            var accessToken = jwtService.generateAccessToken(user.getUsername(),user.getRole());
            return ResponseEntity.ok(new JwtResponse(accessToken));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response){
        var cookie = new Cookie("refresh-token","");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/api/auth/refresh-token");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.noContent().build();
    }
}
