package com.example.demo.service;

import com.example.demo.User;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.ExistingUserException;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService
{
    void createUser(User user) throws ExistingUserException;

    Iterable<UserDto> getUsers(int page, int size);

    Optional<User> getUserByName(String name);

    void deleteUser(Long id);

    UserDto getUserById(Long id);

    Page<User> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
