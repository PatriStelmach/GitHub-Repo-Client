package com.example.demo.Errors;

import com.example.demo.Models.CustomException;
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
        int status = 404;
        String message="User with given username does not exist";
        CustomException error = new CustomException(status, message);

        return new ResponseEntity<CustomException>(error, HttpStatus.NOT_FOUND);
    }

}
