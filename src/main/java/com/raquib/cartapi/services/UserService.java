package com.raquib.cartapi.services;

import com.raquib.cartapi.dtos.UserDto;
import com.raquib.cartapi.entities.User;
import com.raquib.cartapi.exceptions.UserAlreadyExistsException;
import com.raquib.cartapi.exceptions.UserNotFoundException;
import com.raquib.cartapi.mappers.UserMapper;
import com.raquib.cartapi.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


    public UserDto createUser(String userName, String password, String role) {
        boolean existingUser = userRepository.existsById(userName);
        if(existingUser) throw new UserAlreadyExistsException();
        role = role==null || role.isEmpty() ? "ROLE_USER" : role;
        User user = new User(userName,passwordEncoder.encode(password),role);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    public UserDto getUser(String username) {
        var user = userRepository.findById(username).orElseThrow(UserNotFoundException::new);
        return userMapper.toDto(user);
    }
}
