package com.example.demo.service;


import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRepository;
import com.example.demo.exception.Exceptions;
import com.example.demo.exception.ExistingUserException;
import com.sun.istack.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(@NotNull UserDto user) throws ExistingUserException
    {
        checkIfUserExists(user.getName());
        User domainUser = new User(user.getName(), user.getLastName());
        return new UserDto(userRepository.save(domainUser));
    }

    private void checkIfUserExists(String name) throws ExistingUserException
    {
        Optional<User> existingUser = getUserByName(name);

        if (existingUser.isPresent())
        {
            throw new ExistingUserException(Exceptions.EXISTING_USER_EXCEPTION);
        }
    }

    @Override
    public Optional<User> getUserByName(String name)
    {
        return userRepository.findByName(name);
    }

    @Override
    public void deleteUser(long id)
    {
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isPresent())
        {
            userRepository.deleteById(id);
        } else
        {
            throw new UserNotFoundException(id);
        }
    }

    @Override
    public UserDto getUserById(Long id)
    {
        Optional<User> existingUser = userRepository.findById(id);
        return existingUser.map(UserDto::new).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public Page<User> findPaginated(int pageNo, String sortField, String sortDirection)
    {
        final int pageSize = 10;
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.userRepository.findAll(pageable);
    }

}
