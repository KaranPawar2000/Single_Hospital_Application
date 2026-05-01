package com.hospital_single_app.demo.repo;

import com.hospital_single_app.demo.dto.BookingSlotSummaryDTO;
import com.hospital_single_app.demo.entity.TbBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<TbBooking, Long> {

    long countBySlot_PkSlotIdAndBookingDate(Long slotId, LocalDate bookingDate);

    @Query("""
        SELECT new com.hospital_single_app.demo.dto.BookingSlotSummaryDTO(
            s.pkSlotId,
            s.slotName,
            b.bookingDate,
            COUNT(b)
        )
        FROM TbBooking b
        JOIN b.slot s
        WHERE b.bookingDate BETWEEN :startDate AND :endDate
        GROUP BY
            s.pkSlotId,
            s.slotName,
            b.bookingDate
        ORDER BY b.bookingDate, s.pkSlotId
    """)
    List<BookingSlotSummaryDTO> getSlotWiseBookingSummary(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("""
        SELECT b
        FROM TbBooking b
        JOIN b.slot s
        WHERE b.bookingDate = :bookingDate
          AND (:slotId IS NULL OR s.pkSlotId = :slotId)
        ORDER BY b.startTime
    """)
    List<TbBooking> findBookingDetails(
            @Param("bookingDate") LocalDate bookingDate,
            @Param("slotId") Long slotId
    );

}
