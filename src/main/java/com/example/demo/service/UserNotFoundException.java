package com.example.demo.service;


import com.example.demo.exception.Exceptions;

public class UserNotFoundException extends RuntimeException
{
    public UserNotFoundException(Long id)
    {
        super(Exceptions.NOT_FOUND_USER + id);
    }
}
