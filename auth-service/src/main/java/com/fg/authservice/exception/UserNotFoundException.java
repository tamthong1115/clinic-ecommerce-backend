package com.fg.authservice.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;


public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
