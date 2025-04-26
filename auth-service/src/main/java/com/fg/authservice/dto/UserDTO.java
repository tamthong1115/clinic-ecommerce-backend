package com.fg.authservice.dto;

import com.fg.authservice.user.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Data Transfer Object for User.
 */
public record UserDTO(UUID userId, String email, Role role) {
}
