package com.fg.doctorservice.client.user;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {
    private UUID userId;
    private String email;
    private Role role;
}
