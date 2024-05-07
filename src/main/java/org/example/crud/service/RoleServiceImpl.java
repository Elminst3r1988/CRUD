package org.example.crud.service;

import org.example.crud.model.Role;
import org.example.crud.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Set<Role> getDefaultRole() {
        Role roleUser = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found: ROLE_USER"));
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleUser);
        return userRoles;
    }


}
