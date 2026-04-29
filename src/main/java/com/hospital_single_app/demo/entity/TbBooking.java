package com.hospital_single_app.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TbBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_booking_id")
    private Long bookingId;

    // Patient
    @ManyToOne
    @JoinColumn(name = "col_patient_id", nullable = false)
    private TbPatient patient;

    // Date & Time
    @Column(name = "col_booking_date")
    private LocalDate bookingDate;

    @Column(name = "col_start_time")
    private LocalTime startTime;

    @Column(name = "col_end_time")
    private LocalTime endTime;

    // Slot Name
    @ManyToOne
    @JoinColumn(name = "col_slot_id", nullable = false)
    private TbSlot slot;

    // Booking Number
    @Column(name = "col_booking_no", unique = true)
    private String bookingNo;

    // Status
    @Enumerated(EnumType.STRING)
    @Column(name = "col_status")
    private BookingStatus status = BookingStatus.BOOKED;

    // Audit
    @Column(name = "col_created_at")
    private LocalDateTime createdAt;

    @Column(name = "col_updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

