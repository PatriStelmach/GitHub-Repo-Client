package com.example.demo.Controllers;

import com.example.demo.Models.CustomException;
import com.example.demo.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GitControllerAdvice extends ResponseEntityExceptionHandler
{

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomException> handleNotFoundException()
    {
        CustomException error = new CustomException();
        error.setCode(404);
        error.setMessage("User with given username does not exist");

        return new ResponseEntity<CustomException>(error, HttpStatus.NOT_FOUND);
    }

//
}
