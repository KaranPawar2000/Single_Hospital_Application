package com.hospital_single_app.demo.controller;


import com.hospital_single_app.demo.dto.SlotDTO;
import com.hospital_single_app.demo.service.SlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/slots")
@RequiredArgsConstructor
public class SlotController {

    private final SlotService service;


    @PostMapping("/add")
    public ResponseEntity<SlotDTO> add(@RequestBody SlotDTO dto) {
        System.out.println("Request recived");
        return ResponseEntity.ok(service.addSlot(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SlotDTO> update(@PathVariable Long id, @RequestBody SlotDTO dto) {
        return ResponseEntity.ok(service.updateSlot(id, dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SlotDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/available")
    public ResponseEntity<List<SlotDTO>> getAvailableSlots(
            @RequestParam LocalDate date
    ) {
        System.out.println("Request Recived");
        return ResponseEntity.ok(service.getAvailableSlotsByDoctorAndDate(date));
    }


}
