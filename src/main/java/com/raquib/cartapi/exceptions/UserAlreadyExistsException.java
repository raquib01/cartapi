package com.raquib.cartapi.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(){
        super("username already exists");
    }
}
