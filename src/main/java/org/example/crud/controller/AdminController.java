package org.example.crud.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.crud.model.UserProfile;
import org.example.crud.repository.RoleRepository;
import org.example.crud.service.RoleService;
import org.example.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    public String homePage(Model model) {
        model.addAttribute("listUsers", userService.getAllUsers());
        return "admin_users";
    }

    @GetMapping("/showNewUserForm")
    public String showNewUserForm(Model model) {
        UserProfile user = new UserProfile();
        model.addAttribute("user", user);
        return "admin_new";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        UserProfile user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleRepository.findAll());
        return "admin_update";
    }


    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") @Valid UserProfile user,
                             BindingResult bindingResult,
                             @RequestParam(value = "currentPassword") String currentPassword,
                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "admin_update";
        }

        UserProfile existingUser = userService.getUserById(id);
        if (!existingUser.getEmail().equals(user.getEmail()) && !userService.isEmailUnique(user.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "Email уже используется");
            return "admin_update";
        }

        if (!existingUser.getUsername().equals(user.getUsername()) && !userService.isUsernameUnique(user.getUsername())) {
            bindingResult.rejectValue("username", "error.user", "Имя пользователя уже занято");
            return "admin_update";
        }

        String[] roleIds = request.getParameterValues("roles");
        if (!user.getPassword().equals(currentPassword)) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
        }
        userService.updateUser(id, user, roleIds);
        return "redirect:/admin/users";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") @Valid UserProfile user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin_new";
        }
        if (!userService.isUsernameUnique(user.getUsername())) {
            bindingResult.rejectValue("username", "error.user", "Имя пользователя уже занято");
            return "admin_new";
        }

        if (!userService.isEmailUnique(user.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "Email уже используется");
            return "admin_new";
        }

        user.setRoles(roleService.getDefaultRole());
        userService.saveUser(user);
        return "redirect:/admin/users";
    }
}