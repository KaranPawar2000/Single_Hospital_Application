package com.hospital_single_app.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
// * this table is used to send follow up message check how to optimize code *
@Entity
@Table(name = "tb_patient_follow_up")
@Data
public class TbPatientFollowUp {

    // =========================
    // PRIMARY KEY
    // =========================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_follow_up_id")
    private Long followUpId;

    // =========================
    // PATIENT REFERENCE
    // =========================
    @Column(name = "col_patient_id", nullable = false)
    private Long patientId;

    // =========================
    // PATIENT DETAILS (COPY)
    // =========================
    @Column(name = "col_full_name", nullable = false)
    private String fullName;

    @Column(name = "col_phone", length = 15)
    private String phone;

    @Column(name = "col_gender", length = 10)
    private String gender;

    @Column(name = "col_dob")
    private LocalDate dob;

    @Column(name = "col_address", length = 255)
    private String address;

    // =========================
    // FOLLOW-UP DETAILS
    // =========================
    @Column(name = "col_follow_up_date", nullable = false)
    private LocalDate followUpDate;

//    @Column(name = "col_next_follow_up_date")
//    private LocalDate nextFollowUpDate;

}

