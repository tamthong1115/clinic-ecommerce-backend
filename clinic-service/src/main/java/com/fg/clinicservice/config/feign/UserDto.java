package com.fg.clinicservice.config.feign;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    private UUID userId;
    private String userName;
}
