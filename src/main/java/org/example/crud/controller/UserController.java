package org.example.crud.controller;

import org.example.crud.dto.UserProfileDTO;
import org.example.crud.model.UserProfile;
import org.example.crud.repository.UserRepository;
import org.example.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping
    public ModelAndView homePage() {
        return new ModelAndView("admin");
    }

    @GetMapping("/currentUser")
    public UserProfileDTO getCurrentUser(Authentication authentication) {
        UserProfile user = userService.findByUsername(authentication.getName());
        return userService.mapToDTO(user);
    }


    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Validated @RequestBody UserProfileDTO userProfileDTO) {
        Optional<UserProfile> checkUser = userRepository.findByUsername(userProfileDTO.getUsername());
        if (checkUser.isPresent()) {
            return new ResponseEntity<>("Name already exists", HttpStatus.BAD_REQUEST);
        }
        checkUser = userRepository.findByEmail(userProfileDTO.getEmail());
        if (checkUser.isPresent()) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }

        UserProfile createdUser = userService.createUser(userProfileDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Validated @RequestBody UserProfileDTO userProfileDTO) {
        Optional<UserProfile> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        UserProfile existingUser = existingUserOptional.get();

        if (!existingUser.getUsername().equals(userProfileDTO.getUsername()) && userRepository.existsByUsername(userProfileDTO.getUsername())) {
            return new ResponseEntity<>("Name already exists", HttpStatus.BAD_REQUEST);
        }
        if (!existingUser.getEmail().equals(userProfileDTO.getEmail()) && userRepository.existsByEmail(userProfileDTO.getEmail())) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }

        userService.updateUser(userProfileDTO);
        return new ResponseEntity<>(existingUser, HttpStatus.OK);
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




