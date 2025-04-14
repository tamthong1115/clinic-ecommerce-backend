//package com.fg.clinicservice.config.feign;
//
//import com.fg.common.dto.UserDto;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.UUID;
//
//@FeignClient(name = "auth-service")
//public interface AuthClient {
//    @GetMapping("/api/v1/validate")
//    ResponseEntity<UserDto> validateToken(@RequestHeader("Authorization") String token);
//
//    @GetMapping("/api/v1/has-role")
//    boolean hasRole(@RequestParam UUID userId, @RequestParam String role);
//}
