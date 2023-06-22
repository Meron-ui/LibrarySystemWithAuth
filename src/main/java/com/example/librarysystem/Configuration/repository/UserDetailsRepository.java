package com.example.librarysystem.Configuration.repository;

import com.example.librarysystem.Configuration.dto.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserDetailsRepository extends CrudRepository<UserInfo, String> {


    public List<UserInfo> findAll();

    public UserInfo findById(Integer id);

    public void deleteById(Integer id);
    public Boolean existsByUsername(String username);

    public Boolean existsByEmail(String email);


}
