package com.hospital_single_app.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_role_master")
public class TbRoleMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_role_id")   // ✔ final correct column name
    private Long pkRoleId;

    @Column(name = "col_name")
    private String name;

    @Column(name = "col_status")
    private Byte status;

    @Column(name = "col_description")
    private String description;

    @Column(name = "col_created_modified_date")
    private LocalDateTime createdModifiedDate;

    @Column(name = "col_read_only")
    private String readOnly;

    @Column(name = "col_archive_flag")
    private String archiveFlag;

}

