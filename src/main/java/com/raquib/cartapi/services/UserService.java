package com.raquib.cartapi.services;

import com.raquib.cartapi.dtos.CreateUserRequest;
import com.raquib.cartapi.dtos.UserDto;
import com.raquib.cartapi.entities.User;
import com.raquib.cartapi.exceptions.UserAlreadyExistsException;
import com.raquib.cartapi.exceptions.UserNotFoundException;
import com.raquib.cartapi.mappers.UserMapper;
import com.raquib.cartapi.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    public UserDto createUser(String userName, String password, String role) {
        boolean existingUser = userRepository.findById(userName).isPresent();
        if(existingUser) throw new UserAlreadyExistsException();
        role = role==null || role.isEmpty() ? "ROLE_USER" : role;
        User user = new User(userName,password,role);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    public UserDto getUser(String username) {
        var user = userRepository.findById(username).orElseThrow(UserNotFoundException::new);
        return userMapper.toDto(user);
    }
}
