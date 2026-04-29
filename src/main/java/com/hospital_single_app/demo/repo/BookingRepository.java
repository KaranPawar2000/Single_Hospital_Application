package com.hospital_single_app.demo.repo;

import com.hospital_single_app.demo.entity.TbBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface BookingRepository extends JpaRepository<TbBooking, Long> {

    long countBySlot_PkSlotIdAndBookingDate(Long slotId, LocalDate bookingDate);

}
