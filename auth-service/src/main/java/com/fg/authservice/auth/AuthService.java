package com.fg.authservice.auth;

import com.fg.authservice.dto.RegisterRequestDTO;
import com.fg.authservice.exception.InvalidCredentialsException;
import com.fg.authservice.exception.UserAlreadyExistsException;
import com.fg.authservice.exception.UserNotFoundException;
import com.fg.authservice.user.Role;
import com.fg.authservice.user.User;
import com.fg.authservice.user.UserService;
import com.fg.authservice.dto.LoginRequestDTO;
import com.fg.authservice.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;


  public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {
    User user = userService.findByEmail(loginRequestDTO.getEmail())
            .orElseThrow(() -> new UserNotFoundException(
                    String.format("User with email %s not found", loginRequestDTO.getEmail())
            ));

    if(!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
      throw new InvalidCredentialsException("Invalid credentials email or password");
    }

    Optional<String> token = Optional.of(jwtUtil.generateToken(user.getEmail(), user.getRole()));
    return token;
  }

  public User register(RegisterRequestDTO registerRequestDTO) {
    userService.findByEmail(registerRequestDTO.getEmail())
            .ifPresent(user -> {
              throw new UserAlreadyExistsException(
                      String.format("User with email %s already exists", registerRequestDTO.getEmail())
              );
            });

    User user = new User();
    user.setEmail(registerRequestDTO.getEmail());
    user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
    user.setRole(Role.USER);

    return userService.save(user);
  }

  public boolean validateToken(String token) {
    try {
      jwtUtil.validateToken(token);
      return true;
    } catch (JwtException e){
      return false;
    }
  }
}
