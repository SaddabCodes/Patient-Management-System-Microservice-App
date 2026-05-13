package com.sadcodes.patientservice.service;

import com.sadcodes.patientservice.dto.PatientRequestDto;
import com.sadcodes.patientservice.dto.PatientResponseDto;
import com.sadcodes.patientservice.exception.EmailAlreadyExistsException;
import com.sadcodes.patientservice.exception.PatientNotFoundException;
import com.sadcodes.patientservice.grpc.BillingServiceGrpcClient;
import com.sadcodes.patientservice.kafka.KafkaProducer;
import com.sadcodes.patientservice.mapper.PatientMapper;
import com.sadcodes.patientservice.model.Patient;
import com.sadcodes.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public List<PatientResponseDto> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(patient -> PatientMapper.toDto(patient)).toList();
    }

    public PatientResponseDto createPatient(PatientRequestDto patientRequestDto) {
        if (patientRepository.existsByEmail(patientRequestDto.getEmail())) {
            throw new EmailAlreadyExistsException("A patient with this email is already exists" + patientRequestDto.getEmail());
        }
        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDto));

        billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(),
                newPatient.getName(), newPatient.getEmail());

        kafkaProducer.sendEvent(newPatient);
        return PatientMapper.toDto(newPatient);
    }

    public PatientResponseDto updatePatient(UUID id, PatientRequestDto patientRequestDto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient with id not found: " + id));

        if (!patient.getEmail().equals(patientRequestDto.getEmail()) &&
                patientRepository.existsByEmail(patientRequestDto.getEmail())) {
            throw new EmailAlreadyExistsException("A patient with this email is already exists" +
                    patientRequestDto.getEmail());
        }

        patient.setName(patientRequestDto.getName());
        patient.setAddress(patientRequestDto.getAddress());
        patient.setEmail(patientRequestDto.getEmail());
        patient.setDateOfBirth(patientRequestDto.getDateOfBirth().atStartOfDay());

        Patient updatePatient = patientRepository.save(patient);

        return PatientMapper.toDto(updatePatient);
    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }


}
