package com.fg.authservice.dto;

import com.fg.authservice.user.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@RequiredArgsConstructor
public class UserDTO {
    private final UUID id;
    private final String username;
    private final String email;
    private final Role role;
    private final String avatarUrl;
    private final String phone;
    private final String address;
}
