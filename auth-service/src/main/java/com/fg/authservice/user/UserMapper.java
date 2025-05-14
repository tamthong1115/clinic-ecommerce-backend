package com.fg.authservice.user;

import com.fg.authservice.dto.RegisterRequestDTO;
import com.fg.authservice.dto.RegisterRequestWithRoleDTO;
import com.fg.authservice.dto.UserDTO;
import com.fg.authservice.dto.UserLoginResponseDTO;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User toUser(@NonNull RegisterRequestDTO registerRequestDTO) {

        return User.builder()
                .email(registerRequestDTO.getEmail())
                .role(Role.PATIENT)
                .emailVerified(false)
                .build();
    }

    public User toUser(RegisterRequestWithRoleDTO registerRequestWithRoleDTO) {
        return User.builder()
                .email(registerRequestWithRoleDTO.getEmail())
                .role(Role.valueOf(registerRequestWithRoleDTO.getRole()))
                .emailVerified(false)
                .build();
    }

    public UserLoginResponseDTO toUserLoginResponseDTO(@NonNull User user) {
        return new UserLoginResponseDTO(
                user.getUserId(),
                user.getEmail(),
                user.getRole().toString());
    }

    public UserDTO toUserDTO(@NonNull User user) {
        return new UserDTO(
                user.getUserId(),
                user.getEmail(),
                user.getRole()
        );
    }
}