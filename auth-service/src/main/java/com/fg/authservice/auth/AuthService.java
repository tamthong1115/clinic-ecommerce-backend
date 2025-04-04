package com.fg.authservice.auth;

import com.fg.authservice.dto.*;
import com.fg.authservice.exception.InvalidCredentialsException;
import com.fg.authservice.exception.UserAlreadyExistsException;
import com.fg.authservice.exception.UserNotFoundException;
import com.fg.authservice.user.User;
import com.fg.authservice.user.UserMapper;
import com.fg.authservice.user.UserService;
import com.fg.authservice.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserService userService;
  private final JwtUtil jwtUtil;
  private final UserMapper userMapper;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;


  public Optional<LoginResponseDTO> authenticate(LoginRequestDTO loginRequestDTO) {
    try {
      User user = userService.findByEmail(loginRequestDTO.getEmail())
              .orElseThrow(() -> new UserNotFoundException(
                      String.format("User with email %s not found", loginRequestDTO.getEmail())
              ));

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

    return userService.save(newUser);
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
}
