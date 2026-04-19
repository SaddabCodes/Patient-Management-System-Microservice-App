package com.sadcodes.patientservice.mapper;

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
}
