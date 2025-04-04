package com.fg.clinicservice.clinic.service;

import com.fg.clinicservice.clinic.model.Clinic;
import com.fg.clinicservice.clinic.model.ClinicDto;
import com.fg.clinicservice.clinic.model.ClinicForm;
import com.fg.clinicservice.clinic.model.ClinicMapper;
import com.fg.clinicservice.response.ResponseData;
import com.fg.clinicservice.response.ResponseError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClinicServiceImpl implements IClinicService {

    private final  ClinicRepository clinicRepository;

    public ClinicServiceImpl(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    @Override
    public ResponseData<ClinicDto> createNewClinic(ClinicForm clinicForm) {
        Clinic createClinic = ClinicMapper.fromForm(clinicForm);
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
