package com.fg.authservice.dto;

import java.util.UUID;

public record UserLoginResponseDTO(
        UUID id,
        String email,
        String role) {
}
