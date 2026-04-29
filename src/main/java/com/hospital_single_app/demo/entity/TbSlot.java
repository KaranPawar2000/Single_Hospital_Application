package com.hospital_single_app.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TbSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_slot_id")
    private Long pkSlotId;

    @Column(name = "col_slot_name", length = 80, nullable = false)
    private String slotName;

    @Column(name = "col_start_time")
    private LocalTime startTime;

    @Column(name = "col_end_time")
    private LocalTime endTime;

    @Column(name = "col_capacity")
    private Integer capacity;

    @Column(name = "col_seq_no")
    private Integer seqNo;

    @Column(name = "col_status", nullable = false)
    private Boolean status = Boolean.TRUE;


}

