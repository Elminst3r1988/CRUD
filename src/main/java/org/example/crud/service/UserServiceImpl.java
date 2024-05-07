package org.example.crud.service;

import org.example.crud.model.Role;
import org.example.crud.model.UserProfile;
import org.example.crud.repository.RoleRepository;
import org.example.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public List<UserProfile> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(UserProfile userProfile) {
        String encodedPassword = passwordEncoder.encode(userProfile.getPassword());
        userProfile.setPassword(encodedPassword);
        userRepository.save(userProfile);
    }

    @Override
    public UserProfile getUserById(long id) {
        Optional<UserProfile> user = userRepository.findById(id);
        UserProfile userProfile = null;
        if (user.isPresent()) {
            userProfile = user.get();
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
        return userProfile;
    }

    @Override
    public void updateUser(Long id, UserProfile updatedUser, String[] roleIds) {
        Set<Role> roles = new HashSet<>();
        if (roleIds != null) {
            for (String roleId : roleIds) {
                Role role = roleRepository.findById(Long.parseLong(roleId))
                        .orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + roleId));
                roles.add(role);
            }
        }
        userRepository.save(updatedUser);
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    public boolean isUsernameUnique(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }

    public boolean isEmailUnique(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

}

