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
        CustomException error = new CustomException();
        error.setCode(404);
        error.setMessage("User with given username does not exist");

        return new ResponseEntity<CustomException>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PKCS1KeyException.class)
    public ResponseEntity<CustomException> handleKeyException()
    {
        CustomException error = new CustomException();
        error.setCode(404);
        error.setMessage("The PKC1 key file does not exist or has a wrong name. Please check your 'resources' folder.");

        return new ResponseEntity<CustomException>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongAppIdException.class)
    public ResponseEntity<CustomException> handleWrongAppIdException()
    {
        CustomException error = new CustomException();
        error.setCode(401);
        error.setMessage("Wrong GitHub application ID");

        return new ResponseEntity<CustomException>(error, HttpStatus.UNAUTHORIZED);
    }

}
