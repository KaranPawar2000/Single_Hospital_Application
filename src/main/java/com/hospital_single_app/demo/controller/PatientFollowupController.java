package com.hospital_single_app.demo.controller;


import com.hospital_single_app.demo.dto.PatientFollowupDTO;
import com.hospital_single_app.demo.entity.TbPatientFollowUp;
import com.hospital_single_app.demo.service.PatientFollowupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/followup")
@RequiredArgsConstructor
public class PatientFollowupController {

    private final PatientFollowupService service;

    @PostMapping("/save")
    public ResponseEntity<TbPatientFollowUp> saveFollowup(
            @RequestBody PatientFollowupDTO dto
    ) {
        TbPatientFollowUp saved = service.saveFollowup(dto);
        return ResponseEntity.ok(saved);
    }


    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<TbPatientFollowUp>> getByPatient(
            @PathVariable Long patientId
    ) {
        List<TbPatientFollowUp> list = service.getByPatientId(patientId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TbPatientFollowUp>> getByPatient() {
        List<TbPatientFollowUp> list = service.getAllFollowUp();
        return ResponseEntity.ok(list);
    }


}
