package org.example.crud.service.userinfo;

import jakarta.annotation.PostConstruct;
import org.example.crud.model.Role;
import org.example.crud.model.UserProfile;
import org.example.crud.properties.PropertyConfig;
import org.example.crud.repository.RoleRepository;
import org.example.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DataBaseInitService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyConfig propertyConfig;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @PostConstruct
    public void init() {

        if (roleRepository.count() == 0) {

            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);

            UserProfile admin = new UserProfile();

            admin.setId(1L);
            admin.setUsername("admin");
            admin.setAge((short) 25);
            admin.setEmail("admin@admin.ru");
            admin.setPassword(passwordEncoder.encode(propertyConfig.getAdminPass()));

            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("Role not found")));
            roles.add(roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role not found")));

            admin.setRoles(roles);

            userRepository.save(admin);
        }
    }
}