package com.hospital_single_app.demo.controller;


import com.hospital_single_app.demo.dto.PatientDTO;
import com.hospital_single_app.demo.dto.PhoneVerifyDTO;
import com.hospital_single_app.demo.dto.PhoneVerifyRequestDTO;
import com.hospital_single_app.demo.entity.TbPatient;
import com.hospital_single_app.demo.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePatient(
            @PathVariable Long id,
            @RequestBody PatientDTO dto
    ) {
        try {
            return ResponseEntity.ok(patientService.updatePatient(id, dto));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError()
                    .body("Something went wrong while updating patient");
        }
    }

    @GetMapping("/all")
    public List<PatientDTO> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public PatientDTO getPatient(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<PatientDTO> getByPhone(@PathVariable String phone) {
        return ResponseEntity.ok(patientService.getByPhone(phone));
    }

}
