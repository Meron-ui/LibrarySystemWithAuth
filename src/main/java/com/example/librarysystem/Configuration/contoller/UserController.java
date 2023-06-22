package com.example.librarysystem.Configuration.contoller;

import com.example.librarysystem.Configuration.dto.ApiResponse;
import com.example.librarysystem.Configuration.dto.UserInfo;
import com.example.librarysystem.Configuration.model.Role;
import com.example.librarysystem.Configuration.model.RoleName;
import com.example.librarysystem.Configuration.model.Users;
import com.example.librarysystem.Configuration.service.RoleService;
import com.example.librarysystem.Configuration.service.UserDetailsServiceImpl;
import com.example.librarysystem.Configuration.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserInfoService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<UserInfo>> getAllUser(@RequestHeader HttpHeaders requestHeader) {
        List<UserInfo> users = userService.getAllActiveUserInfo();
        if (users == null || users.isEmpty()) {
            System.out.println("Users is empty");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        System.out.println(" /users");
        return ResponseEntity.ok(users);

    }


    @PostMapping("/register")
    public ResponseEntity<ApiResponse> addUser(@RequestBody UserInfo newUser  ) {

        try {
            if(userService.existsByUsername(newUser.getUsername())) {
                return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                        HttpStatus.BAD_REQUEST);
            }

            Role role = new Role();
            Users user = new Users();


            System.out.println(newUser.getUsername() + newUser.getRole());

            role.setRole(RoleName.valueOf(newUser.getRole()));
            user.setUsername(newUser.getUsername());
            user.setPassword(newUser.getPassword());
            user.setEmail(newUser.getEmail());
            user.setActive(newUser.getActive());
            roleService.saveRole(role);
            user.setRoles(Collections.singleton(role));

            Users result = userDetailsService.addUser(user);
            userService.addUser(newUser);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/users/{username}")
                    .buildAndExpand(result.getUsername()).toUri();

            return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));

        }catch(Exception e){
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }

    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserInfo> updateUser(@RequestBody UserInfo userRecord, @PathVariable Integer id) {
        try {
            return ResponseEntity.ok(userService.updateUser(id, userRecord));
        }
        catch(Exception e){
            return new ResponseEntity("Unable to Update", HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/users/changePassword/{id}")
    public ResponseEntity<?> updateUserPassword(@RequestBody UserInfo userRecord, @PathVariable Integer id) {
        try {

            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();

            String username = authentication.getName();
            Users user = userService.getUserInfoByUsername(username);

            if(user.getId() !=id) {
                return new ResponseEntity("Please check either your username or password once again!", HttpStatus.UNAUTHORIZED);

            }
        }
        catch(Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
        return ResponseEntity.ok(userService.updatePassword(id, userRecord));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/changeRole/{id}")
    public ResponseEntity<UserInfo> updateUserRole(@RequestBody UserInfo userRecord, @PathVariable Integer id) {
        try {
            return ResponseEntity.ok(userService.updateRole(id, userRecord));
        }
        catch(Exception e) {
            return new ResponseEntity("Unable to Update", HttpStatus.NOT_FOUND);
        }

    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("Successfully Deleted!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }

    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserInfo> getUserById(@PathVariable Integer id) {
        try {
            UserInfo userInfo = userService.getUserInfoById(id);
            if (userInfo == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(userInfo, HttpStatus.OK);
        }catch(Exception e) {
            return ResponseEntity.badRequest().body(new UserInfo());
        }
    }

}