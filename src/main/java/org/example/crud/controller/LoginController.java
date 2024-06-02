package org.example.crud.controller;

import org.example.crud.service.userinfo.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("loginPage");
    }
}
