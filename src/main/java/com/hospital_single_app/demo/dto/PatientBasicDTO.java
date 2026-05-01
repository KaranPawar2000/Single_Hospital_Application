package com.hospital_single_app.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PatientBasicDTO {

    private Long patientId;
    private String patientName;

}
