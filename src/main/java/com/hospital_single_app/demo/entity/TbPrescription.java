package com.hospital_single_app.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_prescription")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TbPrescription {

    // =========================
    // PRIMARY KEY
    // =========================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_prescription_id")
    private Long pkPrescriptionId;

    // =========================
    // MEDICINE-DOCTOR MAPPING
    // =========================
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "fk_medicine_id",
            nullable = false
    )
    private Medicine medicineDoctor;

    // =========================
    // PATIENT MAPPING
    // =========================
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "fk_patient_id",
            nullable = false
    )
    private TbPatient patient;

    @Column(name = "col_dose", length = 50)
    private String dose;

    // =========================
    // PRESCRIPTION DETAILS
    // =========================


    @Column(name = "col_quantity")
    private Integer quantity;

    @Column(name = "col_instruction", length = 255)
    private String instruction;

    // =========================
    // MEDICINE TIMING
    // =========================
    @Column(name = "col_morning")
    private Boolean morning = false;

    @Column(name = "col_afternoon")
    private Boolean afternoon = false;

    @Column(name = "col_evening")
    private Boolean evening = false;

    @Column(name = "col_night")
    private Boolean night = false;
}

