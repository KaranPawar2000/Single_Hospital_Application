package com.hospital_single_app.demo.utils;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private boolean success;
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private List<String> errors;
}
