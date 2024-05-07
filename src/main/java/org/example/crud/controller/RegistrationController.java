package org.example.crud.controller;

import jakarta.validation.Valid;
import org.example.crud.model.UserProfile;
import org.example.crud.service.RoleService;
import org.example.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RegistrationController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String registerAccount(Model model) {
        UserProfile user = new UserProfile();
        model.addAttribute("user", user);
        return "user_registration";
    }

    @PostMapping("/register/user")
    public String saveAccount(@ModelAttribute("user") @Valid UserProfile user,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user_registration";
        }
        if (!userService.isUsernameUnique(user.getUsername())) {
            bindingResult.rejectValue("username", "error.user", "Имя пользователя уже занято");
            return "user_registration";
        }

        if (!userService.isEmailUnique(user.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "Email уже используется");
            return "user_registration";
        }

        user.setRoles(roleService.getDefaultRole());
        userService.saveUser(user);
        return "redirect:/user/" + user.getId();
    }

}
