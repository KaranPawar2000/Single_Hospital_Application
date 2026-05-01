package com.hospital_single_app.demo.dto;

import lombok.Data;

import java.time.LocalDate;


@Data
public class PatientFollowupDTO {


    private Long patientId;

    private String fullName;
    private String phone;
    private String gender;
    private LocalDate dob;
    private String address;
    private LocalDate followUpDate;



}
