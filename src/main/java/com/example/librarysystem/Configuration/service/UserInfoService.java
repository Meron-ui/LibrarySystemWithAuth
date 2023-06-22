package com.example.librarysystem.Configuration.service;

import com.example.librarysystem.Configuration.dto.UserInfo;
import com.example.librarysystem.Configuration.model.CustomUserDetails;
import com.example.librarysystem.Configuration.model.Users;
import com.example.librarysystem.Configuration.repository.UserDetailsRepository;
import com.example.librarysystem.Configuration.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserInfoService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private UsersRepository usersRepository;


    private final PasswordEncoder passwordEncoder;

    public UserInfoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public Users getUserByUserName(String userName) {
        Optional<Users> usersOptional = usersRepository.findByUsername(userName);

        usersOptional
            .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
        return usersOptional
            .map(CustomUserDetails::new)
            .get();
    }

    public Users getUserInfoByUsername(String username) {
        return usersRepository.findByUsername(username).get();
    }

    public List<UserInfo> getAllActiveUserInfo() {
        return userDetailsRepository.findAll();
    }

    public UserInfo getUserInfoById(Integer id) {
        return userDetailsRepository.findById(id);
    }

    public UserInfo addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        return userDetailsRepository.save(userInfo);
    }

    public UserInfo updateUser(Integer id, UserInfo userRecord) {
        UserInfo userInfo = userDetailsRepository.findById(id);
        userInfo.setUsername(userRecord.getUsername());
        userInfo.setPassword(userRecord.getPassword());
        userInfo.setRole(userRecord.getRole());
        userInfo.setEnabled(userRecord.getEnabled());
        return userDetailsRepository.save(userInfo);
    }

    public void deleteUser(Integer id) {
        usersRepository.deleteById(id);
        userDetailsRepository.deleteById(id);
    }

    public UserInfo updatePassword(Integer id, UserInfo userRecord) {
        UserInfo userInfo = userDetailsRepository.findById(id);
        userInfo.setPassword(passwordEncoder.encode(userRecord.getPassword()));
        Users user = usersRepository.findById(id).get();
        user.setPassword(passwordEncoder.encode(userRecord.getPassword()));

        return userDetailsRepository.save(userInfo);
    }

    public UserInfo updateRole(Integer id, UserInfo userRecord) {
        UserInfo userInfo = userDetailsRepository.findById(id);
        userInfo.setRole(userRecord.getRole());
        return userDetailsRepository.save(userInfo);
    }
    public boolean existsByUsername(String username) {
        return userDetailsRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email){
        return userDetailsRepository.existsByEmail(email);
    }

}
