package com.fg.appointment.mapper;

import com.fg.appointment.dto.AppointmentCreateDTO;
import com.fg.appointment.dto.AppointmentDTO;
import com.fg.appointment.model.Appointment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppointmentMapper {

    /**
     * Maps an AppointmentCreateDTO to a new Appointment entity
     */
    public Appointment toEntity(AppointmentCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        Appointment appointment = new Appointment();
        appointment.setDoctorId(dto.getDoctorId());
        appointment.setDoctorFirstName(dto.getDoctorFirstName());
        appointment.setDoctorLastName(dto.getDoctorLastName());
        appointment.setDoctorProfilePicture(dto.getDoctorProfilePicture());
        appointment.setPatientId(dto.getPatientId());
        appointment.setClinicId(dto.getClinicId());
        appointment.setClinicName(dto.getClinicName());
        appointment.setClinicAddress(dto.getClinicAddress());
        appointment.setServiceId(dto.getServiceId());
        appointment.setServiceName(dto.getServiceName());
        appointment.setServicePrice(dto.getServicePrice());
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setStartTime(dto.getStartTime());
        appointment.setEndTime(dto.getEndTime());
        appointment.setStatus(dto.getStatus() != null ? dto.getStatus() : Appointment.AppointmentStatus.PENDING);
        appointment.setReason(dto.getReason());

        return appointment;
    }

    /**
     * Maps an Appointment entity to an AppointmentDTO
     */
    public AppointmentDTO toDTO(Appointment appointment) {
        if (appointment == null) {
            return null;
        }

        AppointmentDTO dto = new AppointmentDTO();
        dto.setAppointmentId(appointment.getAppointmentId());
        dto.setDoctorId(appointment.getDoctorId());
        dto.setDoctorFirstName(appointment.getDoctorFirstName());
        dto.setDoctorLastName(appointment.getDoctorLastName());
        dto.setDoctorProfilePicture(appointment.getDoctorProfilePicture());
        dto.setPatientId(appointment.getPatientId());
        dto.setClinicId(appointment.getClinicId());
        dto.setClinicName(appointment.getClinicName());
        dto.setClinicAddress(appointment.getClinicAddress());
        dto.setServiceId(appointment.getServiceId());
        dto.setServiceName(appointment.getServiceName());
        dto.setServicePrice(appointment.getServicePrice());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setStartTime(appointment.getStartTime());
        dto.setEndTime(appointment.getEndTime());
        dto.setStatus(appointment.getStatus());
        dto.setReason(appointment.getReason());
        dto.setCreatedAt(appointment.getCreatedAt());
        dto.setUpdatedAt(appointment.getUpdatedAt());

        return dto;
    }

    /**
     * Maps a list of Appointment entities to a list of AppointmentDTOs
     */
    public List<AppointmentDTO> toDTOList(List<Appointment> appointments) {
        return appointments.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing Appointment entity with values from an AppointmentCreateDTO
     */
    public void updateEntityFromDTO(AppointmentCreateDTO dto, Appointment appointment) {
        if (dto == null || appointment == null) {
            return;
        }

        appointment.setDoctorId(dto.getDoctorId());
        appointment.setDoctorFirstName(dto.getDoctorFirstName());
        appointment.setDoctorLastName(dto.getDoctorLastName());
        appointment.setDoctorProfilePicture(dto.getDoctorProfilePicture());
        appointment.setPatientId(dto.getPatientId());
        appointment.setClinicId(dto.getClinicId());
        appointment.setClinicName(dto.getClinicName());
        appointment.setClinicAddress(dto.getClinicAddress());
        appointment.setServiceId(dto.getServiceId());
        appointment.setServiceName(dto.getServiceName());
        appointment.setServicePrice(dto.getServicePrice());
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setStartTime(dto.getStartTime());
        appointment.setEndTime(dto.getEndTime());
        appointment.setStatus(dto.getStatus());
        appointment.setReason(dto.getReason());
    }
}