package com.fg.authservice.handler;

import lombok.Data;
import lombok.Builder;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;

@Data
@Builder
public class ErrorMessageDTO implements Serializable {

    private String message;

    private Instant date;

    private Map<String, String> details;

}