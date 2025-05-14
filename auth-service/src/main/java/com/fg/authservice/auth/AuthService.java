package com.fg.authservice.auth;

import com.fg.authservice.client.patient.PatientClient;
import com.fg.authservice.client.patient.PatientCreateDTO;
import com.fg.authservice.dto.*;
import com.fg.authservice.exception.InvalidCredentialsException;
import com.fg.authservice.exception.UserAlreadyExistsException;
import com.fg.authservice.exception.UserNotFoundException;
import com.fg.authservice.user.User;
import com.fg.authservice.user.UserMapper;
import com.fg.authservice.user.UserService;
import com.fg.authservice.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserService userService;
  private final JwtUtil jwtUtil;
  private final UserMapper userMapper;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;
  private final PatientClient patientClient;

  private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

  public Optional<LoginResponseDTO> authenticate(LoginRequestDTO loginRequestDTO) {
    try {
      User user = userService.findByEmail(loginRequestDTO.getEmail())
              .orElseThrow(() -> new UserNotFoundException(
                      String.format("User with email %s not found", loginRequestDTO.getEmail())
              ));

      // Authenticate the user and add user to the security context
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      loginRequestDTO.getEmail(),
                      loginRequestDTO.getPassword()
              )
      );

      UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
      String token = jwtUtil.generateToken(userDetails);

      UserLoginResponseDTO userLoginResponseDTO = userMapper.toUserLoginResponseDTO(user);
      LoginResponseDTO loginResponseDTO = new LoginResponseDTO(token);
      loginResponseDTO.setUser(userLoginResponseDTO);

      return Optional.of(loginResponseDTO);
    } catch (AuthenticationException e) {
      throw new InvalidCredentialsException("Invalid email or password");
    }
  }

  public User register(RegisterRequestDTO registerRequestDTO) {

    userService.findByEmail(registerRequestDTO.getEmail())
            .ifPresent(user -> {
              throw new UserAlreadyExistsException(
                      String.format("User with email %s already exists", registerRequestDTO.getEmail())
              );
            });

    User newUser = userMapper.toUser(registerRequestDTO);
    newUser.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));


    User savedUser = userService.save(newUser);

    // Create patient after user registration
    try {
      PatientCreateDTO patientCreateDTO = PatientCreateDTO.builder()
              .userId(savedUser.getUserId())
              .email(savedUser.getEmail())
              .build();

      patientClient.createPatient(patientCreateDTO);
      logger.info("Patient created successfully for user with ID: {}", savedUser.getUserId());
    } catch (Exception e) {
      logger.error("Failed to create patient for user with ID: {}", savedUser.getUserId(), e);
    }

    return savedUser;
  }

  public boolean emailExists(String email) {
    return userService.existsByEmail(email);
  }

  public boolean validateToken(String token) {
    try {
      UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.extractUsername(token));
      return jwtUtil.isTokenValid(token, userDetails);
    } catch (JwtException e) {
      return false;
    }
  }

  public UserDTO getCurrentUser(UserDetails userDetails) {
     User user = userService.findByEmail(userDetails.getUsername())
            .orElseThrow(() -> new UserNotFoundException(
                    String.format("User with email %s not found", userDetails.getUsername())
            ));

     return userMapper.toUserDTO(user);

  }

  public UserDTO createUserWithRole(@Valid RegisterRequestWithRoleDTO registerRequestWithRoleDTO) {
    // Check if the email already exists
    if (userService.existsByEmail(registerRequestWithRoleDTO.getEmail())) {
      throw new UserAlreadyExistsException(
              String.format("User with email %s already exists", registerRequestWithRoleDTO.getEmail())
      );
    }

    // Create a new user
    User newUser = userMapper.toUser(registerRequestWithRoleDTO);
    newUser.setPassword(passwordEncoder.encode(registerRequestWithRoleDTO.getPassword()));
    User savedUser = userService.save(newUser);

    return userMapper.toUserDTO(savedUser);
  }

  public void deleteUser(UUID userId) {
    User user = userService.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(
                    String.format("User with ID %s not found", userId)
            ));

    // Delete the user
    userService.delete(user);

    logger.info("User with ID {} deleted successfully", userId);
  }
}
