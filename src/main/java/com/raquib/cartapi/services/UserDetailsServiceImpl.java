package com.raquib.cartapi.services;

import com.raquib.cartapi.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String username){
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        return new User(user.getUsername(),user.getPassword(), Collections.emptyList());
    }

}
