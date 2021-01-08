package com.example.demo.service;


import com.example.demo.entity.User;
import com.example.demo.entity.UserRepository;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.Exceptions;
import com.example.demo.exception.ExistingUserException;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

//    public UserServiceImpl(UserRepository userRepository)
//    {
//        this.userRepository = userRepository;
//    }

    @Override
    public void createUser(@NotNull UserDto user) throws ExistingUserException
    {
        checkIfUserExists(user.getName());
        userRepository.save(new User(user.getName(), user.getLastName()));
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
    public Iterable<UserDto> getUsers(int page, int size)
    {
        Pageable sortedByName =
                PageRequest.of(page, size, Sort.by("name"));

        Page<User> userList = userRepository.findAll(sortedByName);

        return userList
                .stream()
                .map(user ->
                        new UserDto(user.getName(), user.getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> getUserByName(String name)
    {
        return userRepository.findByName(name);
    }

    @Override
    public void deleteUser(long id)
    {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto getUserById(Long id)
    {
        Optional<User> existingUser = userRepository.findById(id);
        return existingUser.map(UserDto::new).orElse(null);
    }

    @Override
    public Page<User> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.userRepository.findAll(pageable);
    }

}
