package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.ExistingUserException;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService
{
    /**
     * Create user service
     * @param user User to be created
     * @throws ExistingUserException Exception if user with name found
     */
    void createUser(UserDto user) throws ExistingUserException;

    /**
     * Check if user with given name found
     * @param name Name to be found
     * @return Optional User
     */
    Optional<User> getUserByName(String name);

    /**
     * Service for deletion of user by ID
     * @param id ID of user to be deleted
     */
    void deleteUser(long id);

    /**
     * Service to search user by ID
     * @param id ID of user
     * @return User found
     */
    UserDto getUserById(Long id);

    /**
     * Find current page
     * @param pageNo Which page
     * @param pageSize Page size
     * @param sortField Sorting of result
     * @param sortDirection which direction to be sorted
     * @return Page with users
     */
    Page<User> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
