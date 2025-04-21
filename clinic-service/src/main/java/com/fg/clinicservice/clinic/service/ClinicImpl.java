package com.fg.clinicservice.clinic.service;

import com.fg.clinicservice.clinic.model.Clinic;
import com.fg.clinicservice.clinic.model.ClinicDto;
import com.fg.clinicservice.clinic.model.ClinicForm;
import com.fg.clinicservice.clinic.model.ClinicMapper;
import com.fg.clinicservice.config.feign.AuthClient;
import com.fg.clinicservice.config.feign.UserDto;
import com.fg.clinicservice.response.ResponseData;
import com.fg.clinicservice.response.ResponseError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClinicImpl implements IClinicService {

    private final  ClinicRepository clinicRepository;
    private final AuthClient authClient;
    private final HttpServletRequest httpServletRequest;

    public ClinicImpl(ClinicRepository clinicRepository, AuthClient authClient, HttpServletRequest httpServletRequest) {
        this.clinicRepository = clinicRepository;
        this.authClient = authClient;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public ResponseData<ClinicDto> createNewClinic(ClinicForm clinicForm) {
        //lay token
        String token = httpServletRequest.getHeader("Authorization");
        //lay userId
        UserDto user = authClient.getCurrentUser().getBody();

        //tao clinic
        Clinic createClinic = ClinicMapper.fromForm(clinicForm);
        createClinic.setUserId(user.getUserId());
        createClinic.setUserName(user.getUserName());
        createClinic.setEmail(user.getEmail());

        //luu va tra kq
        Clinic savedClinic = clinicRepository.save(createClinic);
        ClinicDto clinicDto = ClinicMapper.toDto(savedClinic);

        return new ResponseData<>(201, "clinic created successfully", clinicDto);
    }

    @Override
    public ResponseData<ClinicDto> updateClinic(UUID clinicId,ClinicForm clinicForm) {
        Clinic existingClinic =clinicRepository.findById(clinicId).orElseThrow(()-> new RuntimeException("Clinic not found"));
        ClinicMapper.updateClinicForm(existingClinic,clinicForm);
        Clinic updatedClinic = clinicRepository.save(existingClinic);
        ClinicDto clinicDto = ClinicMapper.toDto(updatedClinic);
        return new ResponseData<>(201, "clinic updated successfully", clinicDto);
    }

    @Override
    public ResponseData<ClinicDto> getClinicById(UUID clinicId) {
        Clinic clinic = clinicRepository.findById(clinicId).orElseThrow(()-> new RuntimeException("clinic not found"));
        ClinicDto clinicDto = ClinicMapper.toDto(clinic);
        return new ResponseData<>(201, "clinic get successfully", clinicDto);
    }


    @Override
    @Transactional
    public ResponseData<String> updateClinicStatus(UUID clinicId, Clinic.Status status) {
        try {
            Clinic clinic = clinicRepository.findById(clinicId)
                    .orElseThrow(() -> new RuntimeException("Clinic not found"));

            clinic.setStatus(status);
            clinicRepository.save(clinic);

            return new ResponseData<>(200, "Clinic status updated successfully", "Updated to: " + status);
        } catch (IllegalArgumentException e) {
            return new ResponseError<>(400, "Invalid status value: " + status);
        } catch (Exception e) {
            return new ResponseError<>(500, "An error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseData<List<ClinicDto>> getAllClinics() {
        List<ClinicDto> listClinic = clinicRepository.findAll().stream()
                .map(ClinicMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseData<>(201, "clinic get successfully", listClinic);
    }


}
