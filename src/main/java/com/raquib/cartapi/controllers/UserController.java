package com.raquib.cartapi.controllers;

import com.raquib.cartapi.dtos.CreateUserRequest;
import com.raquib.cartapi.dtos.UserDto;
import com.raquib.cartapi.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid CreateUserRequest request){
        var userDto = userService.createUser(request.getUsername(),request.getPassword(),request.getRole());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userDto.getUsername()).toUri();
        return ResponseEntity.created(location).body(userDto);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) (authentication != null ? authentication.getPrincipal() : null);
        var userDto = userService.getUser(username);

        return ResponseEntity.ok(userDto);
    }
}
