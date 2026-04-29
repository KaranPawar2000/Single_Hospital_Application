package com.hospital_single_app.demo.dto;

import lombok.Data;
import java.time.LocalTime;

@Data
public class SlotDTO {

    private Long pkSlotId;

    private String slotName;

    private LocalTime startTime;
    private LocalTime endTime;

    private Integer capacity;
    private Integer seqNo;

    private Boolean status;
}

