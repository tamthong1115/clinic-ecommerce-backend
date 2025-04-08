package com.fg.authservice.handler;

import com.fg.authservice.exception.UserAlreadyExistsException;
import com.fg.authservice.exception.InvalidCredentialsException;
import com.fg.authservice.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> handleUserNotFoundException(UserNotFoundException e) {
        ErrorMessageDTO errorMessage = ErrorMessageDTO.builder()
                .message(e.getMessage())
                .date(Instant.now())
                .build();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorMessageDTO> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        ErrorMessageDTO errorMessageDTO = ErrorMessageDTO.builder()
                .message(e.getMessage())
                .date(Instant.now())
                .build();
        return new ResponseEntity<>(errorMessageDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorMessageDTO> handleInvalidCredentialsException(InvalidCredentialsException e) {
        ErrorMessageDTO errorMessageDTO = ErrorMessageDTO.builder()
                .message(e.getMessage())
                .date(Instant.now())
                .build();

        return new ResponseEntity<>(errorMessageDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> response = new HashMap<>();
        StringBuilder errorMessage = new StringBuilder("Validation failed: ");
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorMessage.append(message).append("; ");
        });
        response.put("message", errorMessage.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
