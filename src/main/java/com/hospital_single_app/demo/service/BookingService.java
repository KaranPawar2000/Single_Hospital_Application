package com.hospital_single_app.demo.service;


import com.hospital_single_app.demo.dto.BookingDTO;
import com.hospital_single_app.demo.dto.BookingSlotSummaryDTO;
import com.hospital_single_app.demo.entity.BookingStatus;
import com.hospital_single_app.demo.entity.TbBooking;
import com.hospital_single_app.demo.entity.TbPatient;
import com.hospital_single_app.demo.entity.TbSlot;
import com.hospital_single_app.demo.repo.BookingRepository;
import com.hospital_single_app.demo.repo.PatientRepository;
import com.hospital_single_app.demo.repo.SlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepo;
    private final PatientRepository patientRepo;

    private final SlotRepository slotRepo;

    private BookingDTO toDTO(TbBooking b) {

        BookingDTO dto = new BookingDTO();

        dto.setBookingId(b.getBookingId());
        dto.setBookingDate(b.getBookingDate());
        dto.setStartTime(b.getStartTime());
        dto.setEndTime(b.getEndTime());

        if (b.getSlot() != null) {
            dto.setSlotId(b.getSlot().getPkSlotId());
            dto.setSlotName(b.getSlot().getSlotName());
        }

        dto.setBookingNo(b.getBookingNo());
        dto.setStatus(b.getStatus().name());

        TbPatient p = b.getPatient();
        dto.setPatientId(p.getPatientId());
        dto.setPatientName(p.getFullName());
        dto.setPatientGender(p.getGender());
        dto.setPatientAddress(p.getAddress());
        dto.setPatientPhone(p.getPhone());
        dto.setPatientDob(p.getDob());   // ✅ DOB ADDED

        return dto;
    }

    public BookingDTO addBooking(BookingDTO dto) {

        TbPatient patient = patientRepo.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));


        TbSlot slot = slotRepo.findById(dto.getSlotId())
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        long bookedCount =
                bookingRepo.countBySlot_PkSlotIdAndBookingDate(
                        slot.getPkSlotId(),
                        dto.getBookingDate()
                );

        if (bookedCount >= slot.getCapacity()) {
            throw new RuntimeException(
                    "Slot is fully booked for " + dto.getBookingDate()
            );
        }

        TbBooking booking = TbBooking.builder()
                .patient(patient)
                .slot(slot)
                .bookingDate(dto.getBookingDate())
                .startTime(slot.getStartTime())
                .endTime(slot.getEndTime())
                .bookingNo("BK-" + UUID.randomUUID().toString().substring(0, 8))
                .status(BookingStatus.BOOKED)
                .build();

        return toDTO(bookingRepo.save(booking));
    }

    public BookingDTO updateBooking(Long id, BookingDTO dto) {

        TbBooking booking = bookingRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(BookingStatus.valueOf(dto.getStatus()));
        return toDTO(bookingRepo.save(booking));
    }


    public List<BookingDTO> getAllBookings() {
        return bookingRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<BookingSlotSummaryDTO> getSlotWiseSummary(
            LocalDate startDate,
            LocalDate endDate
    ) {
        return bookingRepo.getSlotWiseBookingSummary(startDate, endDate);
    }

    public List<BookingDTO> getBookingDetails(
            LocalDate bookingDate,
            Long slotId

    ) {
        return bookingRepo.findBookingDetails(
                bookingDate,
                slotId
        ).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public BookingDTO updateBookingStatus(Long bookingId, BookingStatus status) {

        TbBooking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(status);

        return toDTO(bookingRepo.save(booking));
    }

}
