package com.hospital_single_app.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoleMasterDTO {

    private Long pkRoleId;
    private String name;
    private Byte status;
    private String description;
    private LocalDateTime createdModifiedDate;
    private String readOnly;
    private String archiveFlag;
    private String clientName; // optional
}
