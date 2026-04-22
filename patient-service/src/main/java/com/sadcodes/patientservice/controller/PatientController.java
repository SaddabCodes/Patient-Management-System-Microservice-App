package com.sadcodes.patientservice.controller;

import com.sadcodes.patientservice.dto.PatientRequestDto;
import com.sadcodes.patientservice.dto.PatientResponseDto;
import com.sadcodes.patientservice.model.Patient;
import com.sadcodes.patientservice.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;


    @GetMapping
    public ResponseEntity<List<PatientResponseDto>>getPatients(){
        return new ResponseEntity<>(patientService.getPatients(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PatientResponseDto> createPatient(@Valid @RequestBody PatientRequestDto patient){
        return new ResponseEntity<>(patientService.createPatient(patient),HttpStatus.CREATED);
    }
}
