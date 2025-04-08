package com.fg.authservice.user;

import com.fg.authservice.dto.RegisterRequestDTO;
import com.fg.authservice.dto.UserDTO;
import com.fg.authservice.dto.UserLoginResponseDTO;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User toUser(@NonNull RegisterRequestDTO registerRequestDTO) {

        return User.builder()
                .username(registerRequestDTO.getUsername())
                .email(registerRequestDTO.getEmail())
                .role(Role.USER)
                .emailVerified(false)
                .build();
    }

    public UserLoginResponseDTO toUserLoginResponseDTO(@NonNull User user) {
        return new UserLoginResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().toString());
    }

    public UserDTO toUserDTO(@NonNull User user) {
        return new UserDTO(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getRole(),
                user.getAvatarUrl(),
                user.getPhone(),
                user.getAddress()
        );
    }
}