package com.hospital_single_app.demo.service;

import com.hospital_single_app.demo.dto.BookingDTO;
import com.hospital_single_app.demo.dto.HolidayDTO;
import com.hospital_single_app.demo.dto.PatientDTO;
import com.hospital_single_app.demo.dto.SlotDTO;
import com.hospital_single_app.demo.repo.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatFlowService {

    private final PatientService patientService;
    private final HolidayService holidayService;
    private final SlotService slotService;
    private final BookingService bookingService;
    private final BookingRepository bookingRepo;

    public PatientDTO getPatient(String phone) {
        System.out.println(phone);
        try {

            String formatted = phone.substring(2);
            // ⚠️ Fix phone format if needed
            System.out.println(formatted);
            return patientService.getByPhone(formatted);

        } catch (Exception e) {
            // Patient not found OR API error
            return null;
        }
    }

    public void savePatient(PatientDTO dto) {
        patientService.addPatient(dto);
    }

    public List<HolidayDTO> getAllHolidays() {
        return holidayService.getAll();
    }

    public List<SlotDTO> getAllSlots() {
        return slotService.getAll();
    }

    public long getBookedCount(Long slotId, LocalDate date) {
        return bookingRepo.countBySlot_PkSlotIdAndBookingDate(slotId, date);
    }

    public BookingDTO createBooking(BookingDTO dto) {
        return bookingService.addBooking(dto);
    }


}
