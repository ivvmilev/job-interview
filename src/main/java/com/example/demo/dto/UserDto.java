package com.example.demo.dto;

import com.example.demo.entity.User;
import com.sun.istack.NotNull;

public class UserDto
{
    private long id;

    @NotNull
    private String name;

    private String lastName;

    public UserDto()
    {
    }

    public UserDto(String name, String lastName)
    {
        this.name = name;
        this.lastName = lastName;
    }

    public UserDto(User user)
    {
        this.id = user.getId();
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

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }
}
