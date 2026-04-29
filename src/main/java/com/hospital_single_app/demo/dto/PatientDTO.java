package com.hospital_single_app.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientDTO {

    private Long patientId;
    private String fullName;
    private String phone;
    private String gender;

    // ✅ UPDATED FORMAT: dd/MM/yyyy
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dob;


    private String address;
    private Boolean status;



}
