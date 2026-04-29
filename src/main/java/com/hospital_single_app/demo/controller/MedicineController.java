package com.hospital_single_app.demo.controller;


import com.hospital_single_app.demo.dto.MedicineDTO;
import com.hospital_single_app.demo.service.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicine")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    // 🔹 ADD MEDICINE
    @PostMapping("/add")
    public ResponseEntity<MedicineDTO> addMedicine(
            @RequestBody MedicineDTO dto
    ) {

        return ResponseEntity.ok(
                medicineService.addMedicine(dto)
        );
    }

    // 🔹 GET MEDICINES BY DOCTOR ID
    @GetMapping("/get")
    public ResponseEntity<List<MedicineDTO>> getMedicines(
    ) {
        return ResponseEntity.ok(
                medicineService.getMedicines()
        );
    }


}
