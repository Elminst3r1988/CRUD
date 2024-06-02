package org.example.crud.service.userinfo;

import org.example.crud.dto.UserProfileDTO;
import org.example.crud.model.UserProfile;

import java.util.List;

public interface UserService {
    UserProfile createUser(UserProfileDTO userProfileDTO);

    UserProfile updateUser(UserProfileDTO userProfile);

    List<UserProfile> getAllUsers();

    void deleteUser(Long id);

    UserProfile findByUsername(String username);

    UserProfileDTO mapToDTO(UserProfile userProfile);

}
