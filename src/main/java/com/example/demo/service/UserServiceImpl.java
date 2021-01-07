package com.example.demo.service;


import com.example.demo.User;
import com.example.demo.UserRepository;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.Exceptions;
import com.example.demo.exception.ExistingUserException;
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
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User user) throws ExistingUserException
    {
        checkIfUserExists(user);
        userRepository.save(new User(user.getName(), user.getLastName()));
    }

    private void checkIfUserExists(User userDto) throws ExistingUserException
    {
        Optional<User> existingUser = getUserByName(userDto.getName());

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
    public void deleteUser(Long id)
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
