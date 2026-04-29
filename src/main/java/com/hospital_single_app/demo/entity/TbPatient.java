package com.hospital_single_app.demo.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "tb_patient")
@Data
public class TbPatient {

    // =========================
    // PRIMARY KEY
    // =========================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_patient_id")
    private Long patientId;

    // =========================
    // PATIENT DETAILS
    // =========================
    @Column(name = "col_full_name", nullable = false)
    private String fullName;

    // 🔒 UNIQUE PER CLIENT
    @Column(name = "col_phone", nullable = false, length = 15)
    private String phone;

    @Column(name = "col_gender", length = 10)
    private String gender;

    @Column(name = "col_dob")
    private LocalDate dob;

    @Column(name = "col_address", length = 255)
    private String address;

    @Column(name = "col_status")
    private Boolean status = true;

    // =========================
    // CLIENT & STATE
    // =========================


}

