package org.example.crud.service;

import org.example.crud.dto.UserProfileDTO;
import org.example.crud.model.Role;
import org.example.crud.model.UserProfile;
import org.example.crud.repository.RoleRepository;
import org.example.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userProfileRepository;

    @Autowired
    private RoleRepository roleRepository;



    @Override
    @Transactional
    public UserProfile createUser(UserProfileDTO userProfileDTO) {
        return userProfileRepository.save(UserProfile.builder()
                .username(userProfileDTO.getUsername())
                .age(userProfileDTO.getAge())
                .email(userProfileDTO.getEmail())
                .password(userProfileDTO.getPassword())
                .roles(userProfileDTO.getRoles().stream()
                        .map(roleName -> roleRepository.findByName(roleName)
                                .orElseThrow(() -> new RuntimeException("Role not found")))
                        .collect(Collectors.toSet()))
                .build());
    }
    @Override
    @Transactional
    public UserProfile updateUser(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    @Override
    public List<UserProfile> getAllUsers() {
        return userProfileRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userProfileRepository.deleteById(id);
    }

    @Override
    public UserProfile findByUsername(String username) {
        Optional<UserProfile> userProfile = userProfileRepository.findByUsername(username);
        if (userProfile.isPresent()) {
            return userProfile.get();
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    public UserProfileDTO mapToDTO(UserProfile userProfile) {
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setId(userProfile.getId());
        userProfileDTO.setUsername(userProfile.getUsername());
        userProfileDTO.setAge(userProfile.getAge());
        userProfileDTO.setEmail(userProfile.getEmail());
        userProfileDTO.setPassword(userProfile.getPassword());
        userProfileDTO.setRoles(userProfile.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet()));
        return userProfileDTO;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userProfileRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

