package com.example.demo.controller;


import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exception.ExistingUserException;
import com.example.demo.service.UserService;
import com.sun.istack.NotNull;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController
{
    private final UserService userService;

    public UserController(UserService userServiceImpl)
    {
        this.userService = userServiceImpl;
    }

    @GetMapping("/user")
    public Slice<User> viewHomePage(
            @RequestParam(defaultValue = "1") int pageNumber
            , @RequestParam(defaultValue = "name") String sortField
            , @RequestParam(defaultValue = "asc") String order
    )
    {
        return userService.findPaginated(pageNumber, sortField, order);
    }

    @PostMapping("/user")
    public UserDto saveUser(@RequestBody @NotNull UserDto user) throws ExistingUserException
    {
        return userService.createUser(user);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable(value = "id") long id)
    {
        this.userService.deleteUser(id);
    }

    @GetMapping("/page/{pageNo}")
    public Slice<User> findPaginated(@PathVariable(value = "pageNo") int pageNo
                                     , @RequestParam(value = "sortField", defaultValue = "name") String sortField
                                     , @RequestParam(value = "order", defaultValue = "asc") String oder)
    {
        return userService.findPaginated(pageNo, sortField, oder);
    }
}
