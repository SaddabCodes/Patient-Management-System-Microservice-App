package com.sadcodes.patientservice.controller;

import com.sadcodes.patientservice.dto.PatientRequestDto;
import com.sadcodes.patientservice.dto.PatientResponseDto;
import com.sadcodes.patientservice.model.Patient;
import com.sadcodes.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/patients")
@Tag(name = "Patient",description = "API for managing Patients")
public class PatientController {

    private final PatientService patientService;


    @GetMapping
    @Operation(summary = "Get Patients")
    public ResponseEntity<List<PatientResponseDto>> getPatients() {
        return new ResponseEntity<>(patientService.getPatients(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a new Patients")
    public ResponseEntity<PatientResponseDto> createPatient(@Valid @RequestBody PatientRequestDto patient) {
        return new ResponseEntity<>(patientService.createPatient(patient), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Patients")
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable UUID id,
                                                             @RequestBody PatientRequestDto patientRequestDto) {
        return new ResponseEntity<>(patientService.updatePatient(id, patientRequestDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Patients")
   public ResponseEntity<Void> deletePatient(@PathVariable UUID id){
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
   }
}
