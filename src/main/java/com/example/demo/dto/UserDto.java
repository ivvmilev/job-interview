package com.example.demo.dto;

import com.example.demo.entity.User;

public class UserDto
{
    private String name;

    private String lastName;

    public UserDto(String name, String lastName)
    {
        this.name = name;
        this.lastName = lastName;
    }

    public UserDto(User user)
    {
        this.name = user.getName();
        this.lastName = user.getLastName();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
}
