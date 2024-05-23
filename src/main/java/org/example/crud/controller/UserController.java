package org.example.crud.controller;

import org.example.crud.dto.UserProfileDTO;
import org.example.crud.model.UserProfile;
import org.example.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ModelAndView homePage() {
        return new ModelAndView("admin");
    }

    @GetMapping("/currentUser")
    public UserProfileDTO getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        return userService.mapToDTO(userService.findByUsername(username));
    }


    @PostMapping("/create")
    public ResponseEntity<UserProfile> createUser(@Validated @RequestBody UserProfileDTO userProfileDTO) {
        UserProfile createdUser = userService.createUser(userProfileDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<UserProfile> updateUser(@PathVariable Long id, @Validated @RequestBody UserProfile userProfile) {
        UserProfile updatedUser = userService.updateUser(userProfile);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserProfile>> getAllUsers() {
        List<UserProfile> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}




