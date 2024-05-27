package org.example.crud.repository;

import org.example.crud.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUsername(String name);
    Optional<UserProfile> findByEmail(String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
