//package com.fg.clinicservice.controller;
//
//import com.fg.clinicservice.service.CloudinaryService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/v1/cloudinary")
//public class CloudinaryController {
//
//    private final CloudinaryService cloudinaryService;
//
//    @PostMapping("/upload/clinic-room")
//    public ResponseEntity<Map<String, String>> uploadClinicRoomImage(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return ResponseEntity.badRequest().body(Map.of("error", "File cannot be empty"));
//        }
//
//        String imageUrl = cloudinaryService.uploadClinicRoomImage(file);
//        Map<String, String> response = new HashMap<>();
//        response.put("imageUrl", imageUrl);
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }
//
//    @PostMapping("/upload/department")
//    public ResponseEntity<Map<String, String>> uploadDepartmentIcon(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return ResponseEntity.badRequest().body(Map.of("error", "File cannot be empty"));
//        }
//
//        String imageUrl = cloudinaryService.uploadDepartmentIcon(file);
//        Map<String, String> response = new HashMap<>();
//        response.put("imageUrl", imageUrl);
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }
//
//    @PostMapping("/upload/service")
//    public ResponseEntity<Map<String, String>> uploadServiceIcon(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return ResponseEntity.badRequest().body(Map.of("error", "File cannot be empty"));
//        }
//
//        String imageUrl = cloudinaryService.uploadServiceIcon(file);
//        Map<String, String> response = new HashMap<>();
//        response.put("imageUrl", imageUrl);
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }
//
//    @DeleteMapping("/images")
//    public ResponseEntity<Map<String, String>> deleteImage(@RequestParam("url") String imageUrl) {
//        cloudinaryService.deleteImage(imageUrl);
//        return ResponseEntity.ok(Map.of("message", "Image deleted successfully"));
//    }
//}