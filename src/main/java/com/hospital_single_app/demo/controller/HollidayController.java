package com.hospital_single_app.demo.controller;

import com.hospital_single_app.demo.dto.HolidayDTO;
import com.hospital_single_app.demo.service.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/holidays")
@RequiredArgsConstructor
public class HollidayController {

    private final HolidayService service;


    // ===============================
    // ADD HOLIDAY
    // ===============================
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody HolidayDTO dto) {
        try {
            return ResponseEntity.ok(service.addHoliday(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody HolidayDTO dto) {
        try {
            return ResponseEntity.ok(service.updateHoliday(id, dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<HolidayDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }



//    @GetMapping("/available-dates")
//    public ResponseEntity<List<LocalDate>> getAvailableDates(
//            @RequestParam Long doctorId
//    ) {
//        return ResponseEntity.ok(
//                service.getAvailableDatesForDoctor(doctorId)
//        );
//    }


}
