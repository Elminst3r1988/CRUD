package org.example.crud.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserProfileDTO {
    private Long id;
    private String username;
    private Short age;
    private String email;
    private String password;
    private Set<String> roles = new HashSet<>();
}
