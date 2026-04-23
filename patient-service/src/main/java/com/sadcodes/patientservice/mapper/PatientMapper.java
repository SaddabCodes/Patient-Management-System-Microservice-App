package com.sadcodes.patientservice.mapper;

import com.sadcodes.patientservice.dto.PatientRequestDto;
import com.sadcodes.patientservice.dto.PatientResponseDto;
import com.sadcodes.patientservice.model.Patient;


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

        if (dto.getDateOfBirth() == null) {
            throw new RuntimeException("DateOfBirth is null");
        }

        if (dto.getRegisteredDate() == null) {
            throw new RuntimeException("RegisteredDate is null");
        }

        patient.setDateOfBirth(dto.getDateOfBirth().atStartOfDay());
        patient.setRegisteredDate(dto.getRegisteredDate().atStartOfDay());

        return patient;
    }
}
