package com.sadcodes.patientservice.controller;

import com.sadcodes.patientservice.dto.PatientResponseDto;
import com.sadcodes.patientservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
