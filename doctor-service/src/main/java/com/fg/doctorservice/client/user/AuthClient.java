package com.fg.doctorservice.client.user;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "auth-service", contextId = "authServiceClient")
public interface AuthClient {
    @GetMapping("/api/v1/me")
    ResponseEntity<UserDTO> getCurrentUser();

    @GetMapping("/api/v1/email-exists")
    ResponseEntity<Boolean> checkEmailExists(@RequestParam String email);

    @PostMapping("/api/v1/create-with-role")
    ResponseEntity<UserDTO> createUser(@RequestBody RegisterRequestWithRoleDTO request);
}
