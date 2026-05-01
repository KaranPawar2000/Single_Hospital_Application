package com.hospital_single_app.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PhoneVerifyDTO {
    private boolean exists;
    private String message;
    private Long clientId;
    private Integer maxPatientCount;       // 👈 original / limit

    private List<PatientBasicDTO> patients;
}
