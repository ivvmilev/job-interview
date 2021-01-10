package com.example.demo.exception.advice;

import com.example.demo.exception.ExistingUserException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExistingUserAdvice
{
    @ResponseBody
    @ExceptionHandler(ExistingUserException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String userNotFoundHandler(ExistingUserException ex)
    {
        return ex.getMessage();
    }
}
