package com.example.librarysystem.Configuration.repository;

import com.example.librarysystem.Configuration.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByUsername(String username);

}
