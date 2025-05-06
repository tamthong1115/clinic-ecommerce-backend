package com.fg.clinicservice.speciality.service;

import com.fg.clinicservice.response.ResponseData;
import com.fg.clinicservice.speciality.model.Speciality;
import com.fg.clinicservice.speciality.model.SpecialityDto;
import com.fg.clinicservice.speciality.model.SpecialityForm;
import com.fg.clinicservice.speciality.model.SpecialityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SpecialityServiceImpl implements ISpecialityService {

    @Autowired
    private SpecialityRepository specialityRepository;

    public SpecialityServiceImpl(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Override
    public ResponseData<SpecialityDto> createSpeciality(SpecialityForm form) {
        if(form == null)
            throw new IllegalArgumentException("Form cannot be null");

        Speciality createSpeciality = SpecialityMapper.fromForm(form);
        Speciality savedSpeciality = specialityRepository.save(createSpeciality);
        SpecialityDto specialityDto = SpecialityMapper.toDto(savedSpeciality);
        return new ResponseData<>(201, "Speciality created", specialityDto);
    }

    @Override
    public ResponseData<SpecialityDto> updateSpeciality(UUID specialityId ,SpecialityForm form) {
        // Tìm chuyên khoa theo ID
        Speciality speciality = specialityRepository.findById(specialityId)
                .orElseThrow(() -> new RuntimeException("Speciality not found"));

        // Cập nhật các trường nếu không null
        SpecialityMapper.updateSpecialityForm(speciality, form);

        // Lưu lại
        Speciality savedSpeciality = specialityRepository.save(speciality);

        // Chuyển sang DTO và trả về
        SpecialityDto specialityDto = SpecialityMapper.toDto(savedSpeciality);
        return new ResponseData<>(200, "Cập nhật chuyên khoa thành công", specialityDto);
    }

    @Override
    public ResponseData<SpecialityDto> getSpecialityById(UUID specialityId) {
        // TÌm chuyên khoa theo id
        Speciality speciality = specialityRepository.findById(specialityId)
                .orElseThrow(() -> new RuntimeException("Speciality not found"));

        // Chuyển thành DTO và trả về
        SpecialityDto specialityDto = SpecialityMapper.toDto(speciality);
        return new ResponseData<>(200, "Speciality found", specialityDto);
    }

    @Override
    public Page<SpecialityDto> getAllSpeciality(Pageable pageable) {
        // Lấy danh sách chuyên khoa
        Page<Speciality> listSpeciality = specialityRepository.findAll(pageable);

        // Trả về
        return listSpeciality.map(SpecialityMapper::toDto);
    }
}
