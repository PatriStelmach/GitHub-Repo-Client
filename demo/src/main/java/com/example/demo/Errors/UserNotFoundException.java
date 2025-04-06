package com.example.demo.Errors;


public class UserNotFoundException extends RuntimeException
{
    public UserNotFoundException(String message) {
        super(message);
    }
}
