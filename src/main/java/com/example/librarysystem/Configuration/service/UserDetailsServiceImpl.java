package com.example.librarysystem.Configuration.service;


import com.example.librarysystem.Configuration.model.CustomUserDetails;
import com.example.librarysystem.Configuration.model.Users;
import com.example.librarysystem.Configuration.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {


        Optional<Users> usersOptional = usersRepository.findByUsername(userName);

        usersOptional
            .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
        return usersOptional
            .map(CustomUserDetails::new)
            .get();
    }
    public Users addUser(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }
}
