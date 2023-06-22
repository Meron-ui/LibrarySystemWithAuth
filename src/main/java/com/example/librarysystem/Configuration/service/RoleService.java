package com.example.librarysystem.Configuration.service;

import com.example.librarysystem.Configuration.model.Role;
import com.example.librarysystem.Configuration.model.RoleName;
import com.example.librarysystem.Configuration.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role findByRole(RoleName roleName) {
        return roleRepository.findByRole(roleName);

    }

    public Role saveRole(Role role){
        return roleRepository.save(role);
    }

}
