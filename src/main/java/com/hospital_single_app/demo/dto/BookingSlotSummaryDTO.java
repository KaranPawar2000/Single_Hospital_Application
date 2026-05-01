package com.hospital_single_app.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;


@Data
@AllArgsConstructor
public class BookingSlotSummaryDTO {

    private Long slotId;
    private String slotName;
    private LocalDate bookingDate;
    private Long totalBookings;

}
