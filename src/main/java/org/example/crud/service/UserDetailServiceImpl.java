package org.example.crud.service;

import org.example.crud.model.UserProfile;
import org.example.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<UserProfile> account = userRepository.findByUsername(name);

        if (account.isPresent()) {
            UserProfile user = account.get();
            return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
        } else {
            throw new UsernameNotFoundException(name);
        }
    }
}
