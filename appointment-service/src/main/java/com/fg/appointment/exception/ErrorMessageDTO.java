package com.fg.appointment.exception;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
public class ErrorMessageDTO implements Serializable {

    private String code;
    private String message;
    private String timestamp;
    private Map<String, String> details;
}
