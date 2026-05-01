package com.hospital_single_app.demo.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingDTO {

    private Long bookingId;

    private Long patientId;
    private String patientName;
    private String patientGender;
    private String patientAddress;
    private String patientPhone;
    private LocalDate bookingDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long slotId;
    private String slotName;
    private String bookingNo;
    private String status;
    private LocalDate patientDob;
}
