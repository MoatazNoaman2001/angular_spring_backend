package com.moataz.examPlatform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
    private long timestamp;
    private List<String> details;

    public ErrorResponse(int status, String message, long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public ErrorResponse(int status, String message, long timestamp, List<String> details) {
        this(status, message, timestamp);
        this.details = details;
    }
}