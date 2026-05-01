package com.hospital_single_app.demo.controller;


import com.hospital_single_app.demo.dto.PrescriptionDTO;
import com.hospital_single_app.demo.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {


    private final PrescriptionService prescriptionService;


    @PostMapping("/add")
    public ResponseEntity<PrescriptionDTO> addPrescription(
            @RequestBody PrescriptionDTO dto
    ) {
        return ResponseEntity.ok(
                prescriptionService.addPrescription(dto)
        );
    }

    @GetMapping("/all")
    public ResponseEntity<List<PrescriptionDTO>> getAllPrescriptions() {
        return ResponseEntity.ok(
                prescriptionService.getAllPrescriptions()
        );
    }

}
