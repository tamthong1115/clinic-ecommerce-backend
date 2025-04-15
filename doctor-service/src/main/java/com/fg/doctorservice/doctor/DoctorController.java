package com.fg.doctorservice.doctor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Doctor Service";
    }

}
