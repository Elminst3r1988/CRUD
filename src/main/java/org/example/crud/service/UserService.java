package org.example.crud.service;

import org.example.crud.model.UserProfile;

import java.util.List;

public interface UserService {
    List<UserProfile> getAllUsers();
    void saveUser(UserProfile userProfile);

    UserProfile getUserById(long id);

    void deleteUserById(long id);

    void updateUser(Long id, UserProfile updatedUser, String[] roleIds);

    boolean isUsernameUnique(String username);

    boolean isEmailUnique(String email);


}
