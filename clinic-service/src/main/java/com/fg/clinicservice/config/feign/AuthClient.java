package com.fg.clinicservice.config.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service")
public interface AuthClient {
    @GetMapping("api/v1/me")
    ResponseEntity<UserDto> getCurrentUser();
}
