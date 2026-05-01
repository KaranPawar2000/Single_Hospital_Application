package com.hospital_single_app.demo.dto;

import lombok.Data;

@Data
public class PrescriptionDTO {

    private Long prescriptionId;


    private Long medicineId;
    private String medicineName;

    private Long patientId;
    private String patientName;

    private String dose;
    private Integer quantity;
    private String instruction;

    private Boolean morning;
    private Boolean afternoon;
    private Boolean evening;
    private Boolean night;
}
