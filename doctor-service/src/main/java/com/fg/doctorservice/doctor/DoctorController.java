package com.fg.doctorservice.doctor;

import com.fg.doctorservice.doctor.dto.DoctorRequest;
import com.fg.doctorservice.doctor.dto.DoctorBasicResponse;
import com.fg.doctorservice.doctor.dto.DoctorDetailResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<DoctorDetailResponse> createDoctor(@Valid @RequestBody DoctorRequest doctorRequest) {
        return new ResponseEntity<>(doctorService.createDoctor(doctorRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDetailResponse> getDoctorById(@PathVariable UUID id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    @GetMapping
    public ResponseEntity<List<DoctorBasicResponse>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDetailResponse> updateDoctor(@PathVariable UUID id, @Valid @RequestBody DoctorRequest doctorRequest) {
        return ResponseEntity.ok(doctorService.updateDoctor(id, doctorRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable UUID id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<DoctorBasicResponse>> getDoctorsByGender(@PathVariable String gender) {
        return ResponseEntity.ok(doctorService.getDoctorsByGender(gender));
    }

    @GetMapping("/experience/{years}")
    public ResponseEntity<List<DoctorBasicResponse>> getDoctorsByExperienceYears(@PathVariable Integer years) {
        return ResponseEntity.ok(doctorService.getDoctorsByExperienceYears(years));
    }

    @GetMapping("/education/{education}")
    public ResponseEntity<List<DoctorBasicResponse>> getDoctorsByEducation(@PathVariable String education) {
        return ResponseEntity.ok(doctorService.getDoctorsByEducation(education));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<DoctorDetailResponse> getDoctorByEmail(@PathVariable String email) {
        return ResponseEntity.ok(doctorService.getDoctorByEmail(email));
    }
}
