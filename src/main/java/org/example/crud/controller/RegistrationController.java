package org.example.crud.controller;

import org.example.crud.dto.UserProfileDTO;
import org.example.crud.model.UserProfile;
import org.example.crud.repository.UserRepository;
import org.example.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ModelAndView registrationPage() {
        return new ModelAndView("registrationPage");
    }

    @Transactional
    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@Validated @RequestBody UserProfileDTO userProfileDTO) {
        Optional<UserProfile> existingUser = userRepository.findByEmail(userProfileDTO.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists.");
        }
        existingUser = userRepository.findByUsername(userProfileDTO.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists.");
        }
        UserProfile createdUser = userService.createUser(userProfileDTO);
        return ResponseEntity.ok(createdUser);
    }
}