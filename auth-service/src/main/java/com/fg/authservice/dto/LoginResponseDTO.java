 package com.fg.authservice.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginResponseDTO {
    private final String token;
    private UserLoginResponseDTO user;

}
