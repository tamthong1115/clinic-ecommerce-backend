package com.fg.authservice.auth;

import com.fg.authservice.dto.*;
import com.fg.authservice.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;


    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Generate token on user login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody @Valid LoginRequestDTO loginRequestDTO) {

        Optional<LoginResponseDTO> loginResponseOptional = authService.authenticate(loginRequestDTO);

        return loginResponseOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(
            @RequestBody @Valid RegisterRequestDTO registerRequestDTO) {

        authService.register(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterResponseDTO("User registered successfully"));
    }

    @Operation(summary = "Create a user with a specific role")
    @PostMapping("/create-with-role")
    public ResponseEntity<UserDTO> createUserWithRole(
            @RequestBody @Valid RegisterRequestWithRoleDTO registerRequestWithRoleDTO) {

        UserDTO userDTO = authService.createUserWithRole(registerRequestWithRoleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @Operation(summary = "Check if email exists in the system")
    @GetMapping("/email-exists")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = authService.emailExists(email);
        return ResponseEntity.ok(exists);
    }

    @Operation(summary = "Validate Token and return roles")
    @PostMapping("/validate")
    public ResponseEntity<List<String>> validateToken(
            @RequestHeader("Authorization") String authHeader) {

        // Authorization: Bearer <token>
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);
        if (authService.validateToken(token)) {
            List<String> roles = jwtUtil.extractRoles(token);
            return ResponseEntity.ok(roles);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(summary = "Get current user details")
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        UserDTO userDTO = authService.getCurrentUser(userDetails);
        return ResponseEntity.ok(userDTO);
    }


}
