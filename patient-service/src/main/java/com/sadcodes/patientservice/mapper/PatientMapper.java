package com.sadcodes.patientservice.mapper;

import com.sadcodes.patientservice.dto.PatientRequestDto;
import com.sadcodes.patientservice.dto.PatientResponseDto;
import com.sadcodes.patientservice.model.Patient;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PatientMapper {

    public static PatientResponseDto toDto(Patient patient){
        PatientResponseDto patientResponseDto = new PatientResponseDto();
        patientResponseDto.setId(patient.getId().toString());
        patientResponseDto.setName(patient.getName());
        patientResponseDto.setAddress(patient.getAddress());
        patientResponseDto.setEmail(patient.getEmail());
        patientResponseDto.setDateOfBirth(patient.getDateOfBirth().toString());
        return patientResponseDto;

    }

    public static Patient toModel(PatientRequestDto dto){
        Patient patient = new Patient();

        patient.setName(dto.getName());
        patient.setAddress(dto.getAddress());
        patient.setEmail(dto.getEmail());

        // Convert LocalDate → LocalDateTime
        patient.setDateOfBirth(dto.getDateOfBirth().atStartOfDay());
        patient.setRegisterDate(dto.getRegisteredDate().atStartOfDay());

        return patient;
    }
}
