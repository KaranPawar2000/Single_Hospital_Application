package com.hospital_single_app.demo.dto;

import lombok.Data;

@Data
public class UserLoginRequestDTO {
    private String emailId;
    private String password;
}
