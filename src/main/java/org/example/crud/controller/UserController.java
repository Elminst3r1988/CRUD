package org.example.crud.controller;

import org.example.crud.model.UserProfile;
import org.example.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public String homePage(@PathVariable(value = "id") long id, Model model) {
        UserProfile user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user";
    }


}
