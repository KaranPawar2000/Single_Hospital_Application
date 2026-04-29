package com.hospital_single_app.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_medicine")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicine_id")
    private Long medicineId;

    @Column(name = "medicine_name", nullable = false)
    private String medicineName;

    @Column(name = "col_unit")   // ✅ REQUIRED
    private String unit;
}

