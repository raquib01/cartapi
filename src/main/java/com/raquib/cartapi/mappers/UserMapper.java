package com.raquib.cartapi.mappers;

import com.raquib.cartapi.dtos.UserDto;
import com.raquib.cartapi.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
