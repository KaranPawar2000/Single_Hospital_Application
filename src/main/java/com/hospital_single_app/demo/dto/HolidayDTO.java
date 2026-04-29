package com.hospital_single_app.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class HolidayDTO {
    private Long holidayId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate holidayDate;
    private String reason;
    private Boolean status;
}
