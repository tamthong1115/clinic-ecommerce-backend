package com.fg.clinicservice.client.user;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {
    private UUID userId;
    private String email;
    private Role role;
}
