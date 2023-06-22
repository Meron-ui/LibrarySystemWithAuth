package com.example.librarysystem.Configuration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "user")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "username" , unique=true, nullable = false)
    private String username;
    @Column(name = "password" , unique=true, nullable = false)
    private String password;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "active")
    private int active;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns =
    @JoinColumn(name = "user_id"), inverseJoinColumns =
    @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public Users(Users users) {
        this.active = users.active;
        this.email = users.email;
        this.id = users.id;
        this.lastName = users.lastName;
        this.username = users.username;
        this.password = users.password;
        this.roles = users.roles;

    }


}
