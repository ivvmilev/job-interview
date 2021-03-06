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

import java.util.List;
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
    public User createUser(@NotNull User user) throws ExistingUserException
    {
        checkIfUserExists(user.getName(), user.getLastName());
        User domainUser = new User(user.getName(), user.getLastName());
        return userRepository.save(domainUser);
    }

    private void checkIfUserExists(String name, String lastName) throws ExistingUserException
    {
        Optional<User> existingUser = getUserByName(name, lastName);

        if (existingUser.isPresent())
        {
            throw new ExistingUserException(Exceptions.EXISTING_USER_EXCEPTION);
        }
    }

    @Override
    public Optional<User> getUserByName(String name, String lastName)
    {
        return userRepository.findUserByNameAndLastName(name, lastName);
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

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    @Override
    public User getUserById(Long id)
    {
        Optional<User> existingUser = userRepository.findById(id);
        return existingUser.orElseThrow(() -> new UserNotFoundException(id));
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
