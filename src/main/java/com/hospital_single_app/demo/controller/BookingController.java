package com.hospital_single_app.demo.controller;


import com.hospital_single_app.demo.dto.BookingDTO;
import com.hospital_single_app.demo.dto.BookingSlotSummaryDTO;
import com.hospital_single_app.demo.entity.BookingStatus;
import com.hospital_single_app.demo.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService service;


    @PostMapping("/add")
    public BookingDTO addBooking(@RequestBody BookingDTO dto) {
        return service.addBooking(dto);
    }

    @PutMapping("/update/{id}")
    public BookingDTO updateBooking(@PathVariable Long id, @RequestBody BookingDTO dto) {
        return service.updateBooking(id, dto);
    }

    @GetMapping("/all")
    public List<BookingDTO> getAllBookings() {
        return service.getAllBookings();
    }


    @GetMapping("/summary")
    public List<BookingSlotSummaryDTO> getBookingSummary(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate
    ) {
        return service.getSlotWiseSummary(startDate, endDate);
    }

    @GetMapping("/details")
    public List<BookingDTO> getBookingDetails(
            @RequestParam("bookingDate") LocalDate bookingDate,
            @RequestParam(value = "slotId", required = false) Long slotId
    ) {
        return service.getBookingDetails(
                bookingDate,
                slotId
        );
    }

    @PutMapping("/{bookingId}/status")
    public BookingDTO updateBookingStatus(
            @PathVariable Long bookingId,
            @RequestParam BookingStatus status
    ) {
        return service.updateBookingStatus(bookingId, status);
    }



}
