package com.fg.authservice.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;


}
