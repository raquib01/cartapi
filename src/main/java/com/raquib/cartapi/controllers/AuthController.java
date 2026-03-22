package com.raquib.cartapi.controllers;

import com.raquib.cartapi.dtos.LoginRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid LoginRequest loginRequest){
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        System.out.println(auth);

        return ResponseEntity.ok().build();
    }
}
