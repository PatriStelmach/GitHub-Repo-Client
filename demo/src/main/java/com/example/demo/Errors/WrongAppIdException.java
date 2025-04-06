package com.example.demo.Errors;

public class WrongAppIdException extends RuntimeException
{
    public WrongAppIdException(String message) {
        super(message);
    }
}
