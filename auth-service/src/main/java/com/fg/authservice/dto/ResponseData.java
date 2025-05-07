package com.fg.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData<T> {
    private boolean success;
    private String message;
    private T data;
    
    public static <T> ResponseData<T> success(T data) {
        return new ResponseData<>(true, "Operation successful", data);
    }
    
    public static <T> ResponseData<T> success(String message, T data) {
        return new ResponseData<>(true, message, data);
    }
    
    public static <T> ResponseData<T> error(String message) {
        return new ResponseData<>(false, message, null);
    }
}