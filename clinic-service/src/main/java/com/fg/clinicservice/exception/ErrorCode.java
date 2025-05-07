package com.fg.clinicservice.exception;

import com.fasterxml.jackson.annotation.JsonSubTypes;

public enum ErrorCode {
    USER_NOT_FOUND,
    USER_ALREADY_EXISTS,
    INVALID_CREDENTIALS,
    VALIDATION_ERROR,
    INTERNAL_ERROR,
    RESOURCE_NOT_FOUND,
    OPERATION_FAILED,
}
