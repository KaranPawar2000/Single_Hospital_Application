package com.hospital_single_app.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "tb_holiday")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TbHoliday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_holiday_id")
    private Long holidayId;

    @Column(name = "col_date", nullable = false)
    private LocalDate holidayDate;

    @Column(name = "col_reason", nullable = false)
    private String reason;

    @Column(name = "col_status")
    private Boolean status;

}

