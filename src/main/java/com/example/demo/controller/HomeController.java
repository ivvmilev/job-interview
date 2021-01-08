package com.example.demo.controller;


import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exception.ExistingUserException;
import com.example.demo.service.UserService;
import com.sun.istack.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController
{
    private final UserService userService;

    public HomeController(UserService userServiceImpl)
    {
        this.userService = userServiceImpl;
    }

    /**
     * Application main page-get paginated users
     * @return Html page - index
     */
    @GetMapping("/")
    public String viewHomePage(Model model)
    {
        return findPaginated(1, "name", "asc", model);
    }

    /**
     * Creation of new-user page
     * @param model user properties
     * @return Html page new_user
     */
    @GetMapping("/showNewUser")
    public String showNewUser(Model model)
    {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "new_user";
    }

    /**
     * Creation of user page
     * @param user New user created by the user
     * @return return to main page after creation
     * @throws ExistingUserException Throw exception if user with name found
     */
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") @NotNull UserDto user) throws ExistingUserException
    {
        userService.createUser(user);
        return "redirect:/";
    }

    /**
     * Delete user function
     * @param id Id of user to be deleted
     * @return return to main page after deletion
     */
    @RequestMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") long id)
    {
        this.userService.deleteUser(id);
        return "redirect:/";
    }

    /**
     * Pagination functionality
     * @param pageNo Which page to be showed
     * @param sortField Sorted by(name or lastName)
     * @param model attributes for sorting
     * @return Html page index
     */
    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model)
    {
        int pageSize = 5;

        Page<User> page = userService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<User> listUsers = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listUsers", listUsers);
        return "index";
    }
}
