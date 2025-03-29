package com.fg.authservice.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class InvalidCredentialsException extends RuntimeException{
    private String message;

    public InvalidCredentialsException(String message) {}
}
