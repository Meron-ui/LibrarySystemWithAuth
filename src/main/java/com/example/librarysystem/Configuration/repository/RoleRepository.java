package com.example.librarysystem.Configuration.repository;

import com.example.librarysystem.Configuration.model.Role;
import com.example.librarysystem.Configuration.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
   Role findByRole(RoleName roleName);
}

