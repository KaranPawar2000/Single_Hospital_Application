package com.hospital_single_app.demo.controller;


import com.hospital_single_app.demo.dto.PatientDTO;
import com.hospital_single_app.demo.entity.TbPatient;
import com.hospital_single_app.demo.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

private final PatientService patientService;

    @PostMapping("/add")
    public TbPatient addPatient(
            @RequestBody
            PatientDTO dto
    ) {
        return patientService.addPatient(dto);
    }
}
