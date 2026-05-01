package com.hospital_single_app.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserMasterDTO {

    private Long pkUserId;
    private String name;
    private String emailId;
    private String mobileNo;
    private Boolean isActive;
    private String password;       // if you want to hide this, tell me
    private LocalDateTime createdModifiedDate;
    private String readOnly;
    private String archiveFlag;
    private Long roleId;
    private String roleName;
    private String orgName;
}
