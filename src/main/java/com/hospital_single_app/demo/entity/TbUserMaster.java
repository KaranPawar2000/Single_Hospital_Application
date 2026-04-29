package com.hospital_single_app.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_user_master")
@Data
public class TbUserMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_user_id")
    private Long pkUserId;

    @Column(name = "col_name")
    private String name;

    @Column(name = "col_email_id", nullable = false, unique = true)
    private String emailId;

    @Column(name = "col_is_active")
    private Boolean isActive;

    @Column(name = "col_password", nullable = false)
    private String password;

    @Column(name = "col_mobile_no", nullable = false, unique = true)
    private String mobileNo;

    @Column(name = "col_created_modified_date")
    private LocalDateTime createdModifiedDate;

    @Column(name = "col_read_only")
    private String readOnly;

    @Column(name = "col_archive_flag")
    private String archiveFlag;

    @ManyToOne
    @JoinColumn(name = "col_role_id")
    private TbRoleMaster role;


}

